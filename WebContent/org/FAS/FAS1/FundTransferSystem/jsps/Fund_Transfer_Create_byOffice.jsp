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
    <title>Fund Transfer System</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"   media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_FT_fromOffice.js"></script>  
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/PaymentSystem/scripts/Common_PaymentType.js"></script>
    <script type="text/javascript" src="../scripts/Fund_Transfer_Create_byOffice.js"></script>
     <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
   
    
   <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Date_Check.js"></script>
    
  <!--  
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
    --> 
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script> 
    
    <script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" language="javascript">
         function foc()
         {
         
         }
         function loadDate()
         {
        	// call_bankUpdate();
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
                document.frmFundTrs_Create_byOffice.txtCrea_date.value=day+"/"+month+"/"+year;
                call_date(document.frmFundTrs_Create_byOffice.txtCrea_date);  
       }
   </script>
  </head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="LoadAccountingUnitID_Create('LIST_ALL_UNITS');call_clr();setTimeout('loadDate()', 300);foc()" bgcolor="rgb(255,255,225)">
  
    <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Fund Transfer System - From Region/Circle/Division (Create) </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmFundTrs_Create_byOffice" 
          id="frmFundTrs_Create_byOffice" 
          method="POST"
          action="../../../../../Fund_Transfer_Create_byOffice.view?Command=Add"
          onsubmit="return checkNull()">
 
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
               <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice_New(this.value);">
                    <%
                    String mod_id="MF015";
                    String CR_DR="CR";
                    int SUB_AC_HEAD_CODE=0; 
                    int Sub_bankID=0,Sub_branchID=0;
                    long Sub_bankAccNo=0;
                    String Sub_bk_br_city="";
                    int main_AHCODE=0;
                      int unitid=0;
                      String unitname="";
                      try{
                        if(oid==5000)
                        {
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
                <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">                
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
                           maxlength="10" size="11"  readonly="readonly"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);dateCheck(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmFundTrs_Create_byOffice.txtCrea_date,1);"
                         alt="Show Calendar"></img> 
                  </div>
                </td>
              </tr>
           
              
              <tr class="table">
                <td>
                  <div align="left">
                    Voucher Number
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="hidden" name="txtVoucher_No" id="txtVoucher_No"  
                    style="background-color: #ececec"  readonly="readonly" size="6" maxlength="5"/>(System Generated)
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Remittance Type     <font color="#ff2121">* </font>
                          </div>
                </td>
                <td>
                  <div align="left">
                 <select name="radRemitType" id="radRemitType" tabindex="4" onchange="Load_Office_Bank_Details()">
                   <!--  <option value="select">Select</option> -->
                  <option value="U">Unspent</option>
                  <option value="C">Collection</option>
                  <option value="UNM">NRDWP-Main-Int.Transfer</option><!-- changed by sathya on 18/05/2016 previously NM -- Main interest -->
                  <option value="UNS">NRDWP-Support-Int.Transfer</option><!-- previously NS -- Support interest -->
                  <option value="NM">Unspent from NRDWP-Main</option><!-- previously UNM -- Unspent Main  -->
                  <option value="NS">Unspent from NRDWP-Support</option><!-- previously UNS -- Unspent Support -->
                  <option value="UNC">Unspent from NRDWP-Calamity</option>
                 <option value="UNAEJE">Unspent from NRDWP – AE/JE</option> 
                        <option value="NRDWP-WQM-SP">Unspent from NRDWP-WQMSP</option>
                  <option value="FDW">Full Deposit Work</option>
                  <option value="WATCHARGEREV_Hogenakkal">Hogenakkal Office Collection-Water Charges Transfer</option>
                  <option value="WATCHARGEREV">Collection - Water Charges Transfer</option>
                  <option value="NONWATCHARGEREV">Collection - Non Water Charges Transfer</option>
                  <option value="LB100PCNTCONTRIB">Collection - LB 100 Pcnt Contribution</option>
                  <option value="UIDDSMT">Collection - UIDDSMT Fund Including LB Contribution</option>
                  <option value="JICA">Collection - JICA Fund Including LB Contribution</option>
                  <option value="KFW">Collection - KFW Fund Including LB Contribution</option>
                  <option value="FieldKit">Collection - Field Kit</option>
                  <option value="FDW from Collection">Collection - Full Deposit Received</option>
                  <option value="Security Deposit">Collection - SD Withheld or Recovered</option>
                 
                </select> <select style="display: none;" id="txtFDW"  name="txtFDW" onchange="loadAcc_det(this.value);"></select>
               <!--  <input type="button" name="Go_Enter" style="display: none;" id="Go_Enter" value="GO" onclick="goEntry()"> -->
                </div>
              
                </td>
                <!--<td>
                  <div align="left">
                    <input type="radio" name="radRemitType" id="radRemitType" onclick="Load_Office_Bank_Details();"
                           checked="checked" value="U"/>Unspent
                                                        &nbsp;&nbsp;&nbsp;&nbsp;  
                    <input type="radio" name="radRemitType" id="radRemitType" onclick="Load_Office_Bank_Details();"
                           value="C"/>Collection &nbsp;&nbsp;&nbsp;&nbsp;
                             <input type="radio" name="radRemitType" id="radRemitType" onclick="Load_Office_Bank_Details();"
                           value="NM"/>NRDWP-Main-Int.Transfer&nbsp;&nbsp;&nbsp;&nbsp; 
                             <input type="radio" name="radRemitType" id="radRemitType" onclick="Load_Office_Bank_Details();"
                           value="NS"/>NRDWP-Support-Int.Transfer &nbsp;&nbsp;&nbsp;&nbsp;  
                           
                           
                    
                  </div>
                </td>
                
                
              --></tr>
              <%
                    
                    String sql_bank="select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE::varchar,substr(curr.AC_HEAD_CODE::varchar,1,4) as main_AHCODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce (br.CITY_TOWN_NAME,'') as bk_br_city "+
                    " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? "+
                    " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and curr.AC_OPERATIONAL_MODE_ID='OPR' ";
                    // here SL_NO=1 means that DEFAULT account number for that unit ..
                    // But i didn't specify  curr.AC_OPERATIONAL_MODE_ID='OPR' in where condition ,*************
                    //Bcoz for collection and unspent both  using the same Bank account number         *****************
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
                     main_AHCODE=rsbank.getInt("main_AHCODE");              // used in next script let
                     bk_br_city=rsbank.getString("bk_br_city");
                     System.out.println("bank details..."+bankID+" "+branchID+ " "+ bankAccNo+" "+bk_br_city+" "+AC_HEAD_CODE+" " +main_AHCODE);
                    }
                    psbank.close();
                    rsbank.close();
                    
              %>
              <tr class="table">
                <td>
                  <div align="left">Credit A/c Code <font color="#ff2121">
                  <font color="#ff2121">
                     *
                  </font>
                </font>
              </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCash_Acc_code" id="txtCash_Acc_code"   value="<%=AC_HEAD_CODE%>"
                          style="background-color: #ececec" onblur="doFunction('unspent_OR_col_based_bank',null);" readonly="readonly" maxlength="8" size="9"/>
                     </div>
                     <div id="img">
                     <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountNumberList"
                             onclick="MainAccNopopup();"></img> 
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                  Office Bank Account Number
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBankAccountNo"  onkeypress="return numbersonly(event)"   value="<%=bankAccNo%>"
                           id="txtBankAccountNo" maxlength="15"  size="15"  style="background-color: #ececec" readonly="readonly" />
                    
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                   Office Bank Name
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBankName" id="txtBankName" readonly="readonly"   value="<%=bk_br_city%>"
                    style="background-color: #ececec"  size="50" maxlength="49"/>
                   <input type="hidden" name="txtBankId" readonly="readonly"  value="<%=bankID%>"
                           id="txtBankId" size="5" maxlength="5"/>
                   <input type="hidden" name="txtBranchId"  readonly="readonly"  value="<%=branchID%>"
                           id="txtBranchId" size="5" maxlength="5"/>
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">
                    Cheque/DD     <font color="#ff2121">* </font>
                          </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD"
                           value="C"/>Cheque
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD" checked="checked"
                           value="D"/>DD &nbsp;&nbsp;&nbsp;&nbsp; 
                           
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD"
                           value="E" />ECS        
                    
                  </div>
                </td>
              </tr>        
              
              
              
               <tr class="table">
                <td>
                  <div align="left">
                    Cheque/DD Number <font color="#ff2121"> * </font>
                   </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCheque_DD_NO" maxlength="10" onkeypress="return numbersonly(event)"
                           id="txtCheque_DD_NO" onblur="check_dd_cheque(),chequeRange();" size="11"/>                  
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">
                    Cheque/DD/ECS Date<font color="#ff2121"> * </font>
                   </div>
                </td>
                <td>
                  <div align="left">
                  
                    <input type="text" name="txtCheque_DD_date" id="txtCheque_DD_date"  
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>                           
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmFundTrs_Create_byOffice.txtCheque_DD_date,0,2);"
                         alt="Show Calendar"></img>
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
                    <input type="text" name="txtAmount"  onkeypress="return limit_amt(this,event);" onblur="valid_amt(this)"
                           id="txtAmount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
              <!-- This txtSub_Office_code is used for fetch the banking section account number , it's accounting unit is 5
                    It refered in servlet when doFunction('unspent_OR_col_based_bank','null') called-->
              
                <tr class="table" style="display:none">
                <td>
                  <div align="left">
                        Transferred to Office Code
                             <font color="#ff2121">*</font>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtSub_Office_code" readonly="readonly" style="background-color: #ececec" value="5000"
                           id="txtSub_Office_code" maxlength="4"
                           size="6"/>
                    <input type="text" id="txtOfficeName" name="txtOfficeName" size="75" value="HEAD OFFICE - BANKING SECTION- Chennai" readonly="readonly" style="background-color: #ececec" />
                  </div>
                  
                           
                  
                </td>
              </tr>
                <%
                    
                    String sql_bank_section_HO="select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE::varchar,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || br.CITY_TOWN_NAME as bk_br_city "+
                    " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? "+
                     " and curr.AC_HEAD_CODE::varchar like '"+main_AHCODE+"%' "+
                    " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID and  curr.AC_OPERATIONAL_MODE_ID='OPR' ";
                    // here SL_NO=1 means that DEFAULT account number for that unit ..
                     System.out.println(sql_bank_section_HO);
                     Sub_psbank=con.prepareStatement(sql_bank_section_HO);
                     
                     Sub_psbank.setInt(1,5);            // Here i used Banking section unit ID
                     Sub_psbank.setString(2,mod_id);    // Same as above
                     Sub_psbank.setString(3,"DR");     // Always Debit 
                     //Sub_psbank.setInt(4,main_AHCODE);     // to load corresponding Bank head of Banking section based on office head last 4 digits
                     
                     Sub_rsbank=Sub_psbank.executeQuery();
                    if(Sub_rsbank.next())
                    {
                    System.out.println("inside if");
                     Sub_bankID=Sub_rsbank.getInt("BANK_ID");
                     Sub_branchID=Sub_rsbank.getInt("BRANCH_ID");
                     Sub_bankAccNo=Sub_rsbank.getLong("BANK_AC_NO");
                     System.out.println("Sub_bankAccNo..."+Sub_bankAccNo);
                     SUB_AC_HEAD_CODE=Sub_rsbank.getInt("AC_HEAD_CODE");
                     
                     Sub_bk_br_city=Sub_rsbank.getString("bk_br_city");
                        System.out.println("Subbank details..."+bankID+" "+branchID+ " "+ bankAccNo+" "+bk_br_city+" "+AC_HEAD_CODE);
                    }
                    Sub_psbank.close();
                    Sub_rsbank.close();
                    
              %>
             <tr class="table">
                <td>
                  <div align="left">
                            Debit A/c  Code   <font color="#ff2121">*</font>
                           
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtDebitAccCode" value="<%=SUB_AC_HEAD_CODE%>"
                           id="txtDebitAccCode" maxlength="8" style="background-color: #ececec"  readonly="readonly" 
                           onkeypress="return numbersonly(event)"
                           size="9"/>
                   <!-- <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="AccountNumberList"
                             onclick="SubAccNopopup();"></img>   -->  
                  </div>
                </td>
              </tr>
             
           <tr class="table">
                <td>
                  <div align="left">
                   Head Office Bank Account Number  <font color="#ff2121">*</font>
                  
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtSubBankAccountNo" value="<%=Sub_bankAccNo%>"
                           id="txtSubBankAccountNo" maxlength="15"  size="15"  style="background-color: #ececec"  readonly="readonly" />
                    
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    Head Office Bank Name
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtSubBankName" id="txtSubBankName"  value="<%=Sub_bk_br_city%>"
                    style="background-color: #ececec"  readonly="readonly" size="50" maxlength="49"/>
                   <input type="hidden" name="txtSubBankId" value="<%=Sub_bankID%>"
                           id="txtSubBankId" size="5" maxlength="5"/>
                   <input type="hidden" name="txtSubBranchId"  value="<%=Sub_branchID%>"
                           id="txtSubBranchId" size="5" maxlength="5"/>
                  </div>
                </td>
              </tr>
                 <tr class="table">
                <td>
                  <div align="left">
                    Office Reference Number

                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtReferenceNo"
                           id="txtReferenceNo" size="50" maxlength="50"/>
                  </div>
                </td>
              </tr>  
                 <tr class="table">
                <td>
                  <div align="left">
                    Office Reference Date

                  </div>
                </td>
                <td>
                  <div align="left">
                    
                  <input type="text" name="txtReferenceDate" id="txtReferenceDate"                  
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur=" return checkdt(this);;"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmFundTrs_Create_byOffice.txtReferenceDate);"
                         alt="Show Calendar"></img> 
                  </div>
                </td>
              </tr>  
                 <tr class="table">
                <td>
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="60"  onkeypress="return check_leng(this.value);"
                              rows="3"></textarea>
                  </div>
                </td>
              </tr>
            </table>
          </div>
      
      <br>
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
         
        <tbody id="tblListNew" class="table"></tbody></table> 
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center" style="display:block" id="newDiv" name="newDiv">
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
    </form></body>
</html>