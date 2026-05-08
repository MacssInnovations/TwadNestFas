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
    <title>Journal System</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
      
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case_FinalHead_GJV.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType_NegativeAmtAllowed.js"></script>
    <script type="text/javascript" src="../scripts/Journal_General_Edit.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>   
  
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
        document.frmJournal_General_Edit.txtCrea_date.value=day+"/"+month+"/"+year;
         doFunction_voucher('load_Voucher_No','null');
      
         }
        
    
</script>
  </head>
    <%
  String s=request.getContextPath();  
  %>
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
            <font size="4">Edit General Journal System </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmJournal_General_Edit" id="frmJournal_General_Edit" method="POST"
                  action="../../../../../Journal_General_Edit.view?Command=Add"
                  onsubmit="return checkNull()">
                   <input type="hidden" name="sl1_type" id="sl1_type" value="0">
       <input type="hidden" name="acchdcd" id="acchdcd">
                  <input type="hidden" name="acchdcd1" id="acchdcd1">
                  <input type="hidden" name="acchdcd2" id="acchdcd2">
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" >
                     <!-- <option value="0"> Select Account Unit </option>-->
                          <%
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" onchange="byUnitAndOfficeChange();">
                      
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
                        while(rs.next())
                        {
                        	int old_offid=0;

                        	 ps2=con.prepareStatement("select old_office_id from hrm_emp_current_posting where employee_id=?");
                        	                             ps2.setInt(1,empid);
                        	                             rs2=ps2.executeQuery();
                        	                             while(rs2.next())
                        	                             {
                        	                            	 old_offid=old_offid+1;
                        	                             }
                        	                        	if(old_offid !=0)
                        	                        	{
                        	                        		ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? ");
                        	                        	}
                        	                        	else if(old_offid==0)
                        	                        	{
                        	                             ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('CL','NC','RD')");
                        	                        	}
                       // ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
                        ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                        rs2=ps2.executeQuery();
                        if(rs2.next())
                        out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");
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
                           onblur="return call_mainJSP_script(this,1);dateCheck(this);call_date(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_General_Edit.txtCrea_date,1);"
                         alt="Show Calendar"></img>
                    
                           
                    
                  </div>
                </td>
              </tr>
              <!--<tr class="table" style="display:none">
                <td>
                  <div align="left">
                    Cash Book Month & Year
                    
                  </div>
                </td>
                <td>
                  <div align="left">
                  
                    <input type="hidden" name="txtCash_Month_hid"
                           id="txtCash_Month_hid" size="2" maxlength="2"/>
                    <input type="text" name="txtCash_Month" id="txtCash_Month"
                           style="background-color: #ececec"  readonly="readonly"  maxlength="10" size="11"/>
                    <input type="text" name="txtCash_year" id="txtCash_year"
                           style="background-color: #ececec"  readonly="readonly"   maxlength="4" size="5"/>
                  </div>
                </td>
              </tr>-->
              
              <tr class="table">
                <td>
                  <div align="left">
                    Journal&nbsp;Voucher Number
                  </div>
                </td>
                <td>
                  <div align="left">
                  <select size="1" name="txtJournalVou_No" id="txtJournalVou_No"
                            tabindex="4" onchange="doFunction_voucher('load_Voucher_Details','null');">
                      <option value="">--Select Voucher Number--</option>
                    </select>
                  </div>
                </td>
              </tr>
           <tr class="table">
                <td width="30%">
                  <div align="left">
                    Journal Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="5" onchange="enable_cheque(this.value)">
                       <option value="">--Select Journal Type--</option> 
               <%
               
               
             
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
                       unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                       }
                   System.out.println(oid+" "+oname);
                   ps.close();
                   rs.close();
                   }
               }
               catch(Exception e)
               {
                   System.out.println(e);
               }
               System.out.println("unitid::::"+unitid);
               if(unitid==3)
               {
            	   try
                   {
                          ps=con.prepareStatement("select JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where CATEGORY='G' and DISPLAY_RESTRICTED!='Y' and JOURNAL_TYPE_CODE not in ( 64, 65)  order by JOURNAL_TYPE_DESC");
                          rs=ps.executeQuery();
                          while(rs.next())
                          {
                          out.println("<option value="+rs.getInt("JOURNAL_TYPE_CODE")+">"+rs.getString("JOURNAL_TYPE_DESC")+"</option>");
                         
                          }
                          out.println("<option value=101>"+"Transferrable To NRDWP Schemes - HO"+"</option>");
                  } 
                  catch(Exception e)
                  {
                  System.out.println("Exception in Journal combo..."+e);
                  }
                  finally
                  {
                  rs.close();
                  ps.close();
                  } 
               }
               else{
                     try
                     {
                            ps=con.prepareStatement("select JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where CATEGORY='G' and DISPLAY_RESTRICTED!='Y' and JOURNAL_TYPE_CODE not in ( 64, 65)  order by JOURNAL_TYPE_DESC");
                            rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getInt("JOURNAL_TYPE_CODE")+">"+rs.getString("JOURNAL_TYPE_DESC")+"</option>");
                            }
                        
                    } 
                    catch(Exception e)
                    {
                    System.out.println("Exception in Journal combo..."+e);
                    }
                    finally
                    {
                    rs.close();
                    ps.close();
                    } 
               }
                %>
                    </select>
                     <!-- The following field 'cmbMas_SL_Code ' doesn't used for anything , but this field name is refered in common_Function_SL_code.js ,
                            that's why this si simply added here -->
                     <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code" tabindex="4" style="display:none" >
                     </select>
                     <!-- the above is no use here , so it is always style.display='none' -->
                  </div>
                </td>
              </tr>
              </table>
              <div style="display:none" id="CHD" >
              <table cellspacing="1" cellpadding="2" border="1" width="100%">
               <tr class="table" >
                <td width="30%">
                  <div align="left">
                    Cheque Number and Date
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCheque_NO" maxlength="10" tabindex="6" onkeypress="return numbersonly(event)"
                           id="txtCheque_NO" size="11"/>
                     <input type="text" name="txtCheque_date" id="txtCheque_date"  tabindex="7" 
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_General_Edit.txtCheque_date);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
              </table>
              </div>
              
              <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="table">
                <td width="30%">
                  <div align="left">Remarks <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="8"  onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
               <!-- <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>Modification Details</strong>
                  </div>
                </td>
              </tr>
                  <tr class="table">
                <td>
                  <div align="left">
                   Authorized By
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="hidden" name="txtAuth_By"
                           id="txtAuth_By" size="6"/>
                    <input type="text" name="Auth_By"  style="background-color: #ececec" readonly="readonly"
                           id="Auth_By" size="50"/>
                     <img src="../../../../../images/c-lovi.gif" width="20"
                     height="20" alt="empList" onclick="servicepopup();"></img>      
                  </div>
                </td>
              </tr>
                <tr class="table">
                    <td>
                      <div align="left">
                        Reference Number 
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtReferNO_edit" id="txtReferNO_edit"                              
                               maxlength="15" size="16"/>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
                        Reference Date                 
                      </div>
                    </td>
                    <td>
                      <div align="left">
                          <input type="text" name="txtReferDate_edit" id="txtReferDate_edit"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmJournal_General_Edit.txtReferDate_edit);"
                         alt="Show Calendar"></img>
                      </div>
                    </td>
                 </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
                        Remarks 
                        
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <textarea name="txtRemak_edit" id="txtRemak_edit" cols="70" onkeypress="return check_leng(this.value);"
                              rows="3"></textarea>
                      </div>
                    </td>
                  </tr>-->
           
             <!-- <tr class="table">
                <td>
                  <div align="left">
                    Total Amount (Rs. P.) 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAmount"  onkeypress="return limit_amt(this,event);"  onblur="valid_amt(this);"
                           id="txtAmount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>-->
            </table>
          </div>
        </div>
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" > Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
                    <table cellspacing="1" cellpadding="2" border="1"
                           width="100%">
                      <tr class="tdTitle">
                        <td colspan="2">
                          <div align="left">
                            <strong> Details</strong>
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
                            onchange="sixdigit();Acc_HeadCodeValidation();" 
                            onblur="headcode();HeadCodeValidation1('<%=s %>');Acc_HeadCodeValidation();"  size="9"/>
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
                  <div align="left">Sub-Ledger Type  <font color="#ff2121">*</font> </div>
                </td>
                <td width="60%">
                  <div align="left">
                   <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="fordcb(this.value)">
                      <option value="">--Select Type--</option>
                     
                    </select>
                  </div>
                  <div id="benifici" style="display:none">
                   <select size="1" name="dcb_ben_type" id="dcb_ben_type"   onchange="call('get','null')"  >
                      <option value="">--Select Type--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getInt("BEN_TYPE_ID")+">"+rs.getString("BEN_TYPE_DESC")+"</option>");
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
                  <div align="left">Sub-Ledger Code <font color="#ff2121">*</font> </div>
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
                            Bill Number 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtBill_NO" maxlength="10" onkeypress="return numbersonly(event)"
                                   id="txtBill_NO" size="11"/>
                          </div>
                        </td>
                      </tr>
                      <tr class="table">
                        <td>
                          <div align="left">
                            Bill Date 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtBill_date"
                                   id="txtBill_date" maxlength="10" size="11"
                                   onfocus="javascript:vDateType='3';"
                                   onkeypress="return calins(event,this);"
                                   onblur="return checkdt(this);"/>
                             
                            <img src="../../../../../images/calendr3.gif"
                                 onclick="showCalendarControl(document.frmJournal_General_Edit.txtBill_date);"
                                 alt="Show Calendar"></img>
                          </div>
                        </td>
                      </tr>
                      <tr class="table">
                        <td>
                          <div align="left">
                            Bill Type 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtBill_type"
                                   id="txtBill_type" size="50"/>
                          </div>
                        </td>
                      </tr>
                      <tr class="table">
                        <td>
                          <div align="left">
                            Agreement Number 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtAgree_No"
                                   id="txtAgree_No" size="11" maxlength="10"/>
                          </div>
                        </td>
                      </tr>
                      <tr class="table">
                        <td>
                          <div align="left">
                            Agreement Date 
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtAgree_Date"
                                   id="txtAgree_Date" maxlength="10" size="11"
                                   onfocus="javascript:vDateType='3';"
                                   onkeypress="return calins(event,this);"
                                   onblur="return checkdt(this);"/>
                             
                            <img src="../../../../../images/calendr3.gif"
                                 onclick="showCalendarControl(document.frmJournal_General_Edit.txtAgree_Date);"
                                 alt="Show Calendar"></img>
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
                    <input type="text" name="txtsub_Amount" onkeypress="return limit_amt_journal(this,event);" onblur="valid_amt(this);"
                           id="txtsub_Amount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    Adjustment Year & Month
                    
                  </div>
                </td>
                <td>
                  <div align="left">
                     <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" >
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
           <option value="0">--Select Month--</option>
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
                  <div align="left">Particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtParticular" id="txtParticular" cols="70"  onkeypress="return chk_part_len(this.value);"
                              rows="3"></textarea>
                  </div>
                </td>
              </tr>
                      <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="cmdadd" id="cmdadd"
                                 value="ADD" onclick="checkSubLedgerMandatory()" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate" value="UPDATE"
                                 id="cmdupdate" onclick="checkSubLedgerMandatory1()"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddelete" value="DELETE"
                                 id="cmddelete" onclick="delete_GRID()"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclear" value="CLEAR ALL"
                                 id="cmdclear" onclick="clearall()"/></td>
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
                        <th >Select</th>
                        <th >A/c Head Code</th>
                        <th >CR/DR</th>
                        <th>Sub Ledger Type</th>
                        <th >Sub Ledger Code</th>
                          <th>Bill No.</th>
                        <th>Bill Date</th>  
                        <th >Amount</th>
                        
                      </tr>
                       <tbody id="grid_body" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
               
              </tr>
            </table>
            
             <input type="hidden" name="CB_REF_NO" id="CB_REF_NO" value="0">
                 <input type="hidden" name="CB_REF_DATE" id="CB_REF_DATE" value="">
                  <input type="hidden" name="MULTIPLE_PVRS" id="MULTIPLE_PVRS" value="">
                  <input type="hidden" name="MULTIPLE_PVR_DETAILS" id="MULTIPLE_PVR_DETAILS" value="">
                  <input type="hidden" name="CB_TDCA_REF_NO" id="CB_TDCA_REF_NO" value="">
                  <input type="hidden" name="CB_TDCA_REF_DATE" id="CB_TDCA_REF_DATE" value="">
                  <input type="hidden" name="CB_TPA_REF_NO" id="CB_TPA_REF_NO" value="">
                  <input type="hidden" name="CB_TPA_REF_DATE" id="CB_TPA_REF_DATE" value="">
                  
                  <input type="hidden" name="VERIFIED" id="VERIFIED" value="">
                  <input type="hidden" name="VERIFIED_DATE" id="VERIFIED_DATE" value="">
                  <input type="hidden" name="VERIFIED_AUTHORITY" id="VERIFIED_AUTHORITY" value="">
                  
            
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
    </form></body>
</html>