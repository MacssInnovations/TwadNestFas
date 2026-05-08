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
    <meta http-equiv="pragma" content="no-cache">
    <title>Cheque Cancellation System</title>
     <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../scripts/Cheque_Cancellation_System.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <!--script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Date_Check.js"></script-->  
   <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_forJOURNAL.js"></script>
     <!--script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_forPAY.js"></script-->
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
            document.frmCheque_Cancel.txtCrea_date.value=day+"/"+month+"/"+year;
        //    doFunction_voucher('load_Voucher_No','null')
           
      }
    
</script>
<script type="text/javascript" language="javascript">
         function foc()
         {
         }
         function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
         
         function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmCheque_Cancel.txtCB_Year.value=year;
        document.frmCheque_Cancel.txtCB_Month.value=month;
        document.frmCheque_Cancel.txtCash_year.value=year;
        document.frmCheque_Cancel.txtCash_Month.value=month;
   
        
         }
</script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="loadyear_month();loadDate();call_clr();" bgcolor="rgb(255,255,225)">
  
  
  
  
  
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Cheque Cancellation System</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmCheque_Cancel" id="frmCheque_Cancel"  onsubmit="return checkNull()" 
             action=""  >
      
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                    
                          <%
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
                        
                        int countoffice=0;      
                        while(rs.next())
                        {
                            ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
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
                    Cheque Cancellation Date
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
                         onclick="showCalendarControl(document.frmCheque_Cancel.txtCrea_date,1);"
                         alt="Show Calendar"></img> 
                                    
                  </div>
                </td>
              </tr>
               
            
             <tr class="table" >
             <td>
             
                Select the Option </td>
                  <td>
                  Monthwise
                    <input type="radio" name="txtoption" id="txtoption" tabindex="15"
                            value="M"   onclick="checkoption(this.value)" >
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                  Chequewise
                    <input type="radio" name="txtoption" id="txtoption" onclick="checkoption(this.value)"
                           value="C"  > &nbsp;&nbsp;&nbsp;&nbsp; 
        
                </td>
             </tr>
           
          <tr align="left" id="optionid" style="display:block">
          
          <td class="table">
          
        
              Select Cash Book Year &amp; Month
             
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" >
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onchange="doFunction_voucher('chequenodetails','null')">
          <!--<option value="">select the Month</option>-->
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
        
         <tr class="table" style="display:none">
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
              </tr>
           
           <tr class="table" >
                <td>
                  <div align="left">
                    Cheque/DD Number  
                                         
                  </div>
                </td>
                           <td>
                  <div align="left">
                    <select size="1" name="txtCheque_No" id="txtCheque_No" 
                            tabindex="5"  onchange="doFunction_voucher('Voucher_Details','null');">
                          
                      <option value="">
                        --Select Cheque/DD Number--
                      </option>
                      
                    </select>
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
                            tabindex="5"  onchange="doFunction_voucher('Other_Details','null');">
                          
                      <option value="">
                        --Select Voucher Number--
                      </option>
                      
                    </select>
                  <!--input type="text" name="txtReceipt_No" id="txtReceipt_No" tabindex="8" style="background-color: #ececec"  readonly="readonly" </input-->
                    
                  </div>
                </td>

              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Document Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtDoc_date" id="txtDoc_date" tabindex="6" 
                           maxlength="10" size="11" style="background-color: #ececec"  readonly="readonly"   >
                   
                  </div>
                </td>
                </tr>
                 
              <tr class="table" >
              <td> Document Type </td>
              <td> <input type="text" name="doc_type" id="doc_type" tabindex="7" style="background-color: #ececec"  readonly="readonly"  ></input>
              </td>
              </tr>
                      
              
              
           
                      <tr class="table">
                      <td>
                  <div align="left">
                    Cheque Date & Cheque/DD
                    <font color="#ff2121"> * </font>
                   </div>
                </td>
                <td>
                <div align="left">
                    <input type="text" name="txtCheque_DD_date" id="txtCheque_DD_date" tabindex="9" 
                           maxlength="10" size="11"  style="background-color: #ececec"  readonly="readonly" 
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                    <input type="text" name="txtCheque_DD" tabindex="10" maxlength="10"  style="background-color: #ececec"  readonly="readonly" 
                           id="txtCheque_DD" size="11"/>       
                  
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
                    <input type="text" name="txtAmount"  onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);" tabindex="11" 
                        readonly="readonly"   style="background-color: #ececec"  id="txtAmount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Reason for Cancellation</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="60"  tabindex="12" 
                                rows="3"></textarea>
                  </div>
                </td>
              </tr>
              <tr class="table">
              
                <td>
                  <div align="left">
                   Issued Fresh Cheque/DD     <font color="#ff2121">* </font>
                          </div>
                </td>
                <td>
                  
                    <input type="radio" name="txtCheque_DD2" id="txtCheque_DD2" tabindex="13"
                            value="Y" checked   onclick="cheque(this.value)">Yes
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtCheque_DD2" id="txtCheque_DD2" tabindex="14"
                           value="N" onclick="cheque(this.value)">No &nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                
                        
                </tr>
                
                </table>
                <div align="left" id="div_cheque" display="none" >
             <table cellspacing="1" cellpadding="2" border="1" width="100%"> 
             <tr class="table">
             <td>
                  Created Fresh Payment Voucher </td>
                  <td>
                  
                    <input type="radio" name="txtCheque_DD3" id="txtCheque_DD3" tabindex="15"
                            value="Y" checked  onchange="check_amount(this.value)" >Yes
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtCheque_DD3" id="txtCheque_DD3" onchange="check_amount(this.value)"
                           value="N"  >No &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="button" id="viewbtn" name="viewbtn" value="View" onclick="ListCheq();"/>
         
                </td>
             </tr>
             
                 <tr class="table">
                 
                <td width="26%">
                  <div align="left">
                    Issued New Cheque Number & Date
                    <font color="#ff2121"> * </font>
                   </div>
                </td>
                <td width="74%">
                    <div align="left">
                   <!-- <input type="text" name="txtCheque_DD_NO2" maxlength="10"  onkeypress="return numbersonly(event)" onfocus="ListCheq()"   
                           id="txtCheque_DD_NO2" onchange="check_oldcheqno(this,event)" onblur="check_dd_cheque()" size="11"/>  -->
                     <input type="text" name="txtCheque_DD_NO2" maxlength="10"    onkeypress="return numbersonly(event)"
                           id="txtCheque_DD_NO2" onchange="check_oldcheqno(this,event);" onblur="check_dd_cheque();" size="11"/>      
                    <input type="text" name="txtCheque_DD_date2" id="txtCheque_DD_date2" 
                           maxlength="10" size="11"    
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="check_currentdate(this);return checkdt(this)"
                           />
                   
                  </div>
                
                </td>
              
              </tr>
           <tr class="table">
                <td>
                  <div align="left">
                    New Cheque/DD Type
                            <font color="#ff2121"> *</font>
                          </div>
                </td>
                <td>
                  <div align="left">
                  
                    <input type="radio" name="txtCheq_DD_Issued" id="txtCheq_DD_Issued" 
                           value="C"/>Cheque
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtCheq_DD_Issued" id="txtCheq_DD_Issued"  
                           value="D"/>DD &nbsp;&nbsp;&nbsp;&nbsp; 
                           
                           
                    
                  </div>
                </td>
              </tr>
             
             
              <tr class="table">
               
                <td width="26%">
                  <div align="left">
                    Issued New Cheque Amount (Rs. P.) 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="74%">
                  <div align="left">
                    <input type="text" name="txtAmount2"  onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);" 
                          id="txtAmount2" maxlength="17" size="18"/>
                  </div>
                </td>
            
              </tr>

            </table>
           </div>
         
         
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                
                <input type="SUBMIT" name="butSub" tabindex="19" id="butSub" value="SUBMIT" onclick="check_currentdate(this);doFunction_voucher('Add','null')"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" tabindex="20" value="CANCEL"
                       onclick="call_clr();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT" tabindex="21"
                       onclick="self.close();"/>
              </div>
              
              
            </td>
          </tr>
        </table>
        
       
    
    </form>
    
    </body>
</html>