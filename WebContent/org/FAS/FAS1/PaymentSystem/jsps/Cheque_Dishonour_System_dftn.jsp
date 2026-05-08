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
    <title>Cheque Dishonour System</title>
     <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../scripts/Cheque_Dishonour_System_dftn.js"></script>
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
  <!--   <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  --> 
   <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_forRECEIPT.js"></script>
     
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
            document.frmCheque_Dishonour.txtCrea_date.value=day+"/"+month+"/"+year;
          //  doFunction_voucher('chequenodetails','null')
           
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
       
        document.frmCheque_Dishonour.txtCB_Year.value=year;
        document.frmCheque_Dishonour.txtCB_Month.value=month;
        document.frmCheque_Dishonour.txtCash_year.value=year;
        document.frmCheque_Dishonour.txtCash_Month.value=month;
        //document.frmPhysicalCashBalance.txtverify_date.value=day+"/"+month+"/"+year;
         }
</script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="loadyear_month();loadDate();call_clr();doFunction_voucher('chequenodetails','null')" bgcolor="rgb(255,255,225)">
  
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Cheque Dishonour System</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmCheque_Dishonour" id="frmCheque_Dishonour" method="POST" onsubmit="return checkNull()" 
             action="../../../../../Cheque_Dishonour_System.view?Command=Add"  >
     
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
                    
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                     <!-- <option value="0"> Select Account Unit </option>-->
                          <%
                      int unitid=0;
                      int office_id=0;
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
                              out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"("+rs.getInt("ACCOUNTING_UNIT_ID")+")"+"</option>");
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
                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"("+rs.getInt("ACCOUNTING_UNIT_ID")+")"+"</option>");
                                  unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                  }
                                  
                              }
                          }
                      catch(Exception e)
                        {
                            System.out.println(e);
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
                        out.println("<option value="+oid+">"+"HEAD OFFICE"+"("+oid+")"+"</option>");
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
                            ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
                            ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                            rs2=ps2.executeQuery();
                            if(rs2.next())
                            {
                              out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"("+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+")"+"</option>");                           
                              office_id=rs.getInt("ACCOUNTING_FOR_OFFICE_ID");
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
                    Accounting For Old Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    
                    <select size="1" name="cmbAcc_UnitCode_old" id="cmbAcc_UnitCode_old" tabindex="1">
                    <%
                   System.out.println("here");
                   System.out.println(oid+"  " +oname);
                    ResultSet rs21=null;
                try
                {
                   if(oid==5000)
                    {
                        out.println("<option value="+oid+">"+"HEAD OFFICE"+"("+oid+")"+"</option>");
                    }
                    else
                    {
                    ps=con.prepareStatement("select distinct OLD_OFFICE_ID from PMS_DCB_MST_BENEFICIARY where OFFICE_ID=?");
                	ps.setInt(1,office_id);
                	rs21=ps.executeQuery();
                    while(rs21.next())
                        {
                    ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCT_TRF_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?");
                    ps.setInt(1,unitid);
                    ps.setInt(2,rs21.getInt("OLD_OFFICE_ID"));
                    rs=ps.executeQuery();
                    
                    if(rs.next())
                                  {
                                  System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                  System.out.println(rs.getString("ACCOUNTING_UNIT_NAME"));
                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"("+rs.getInt("ACCOUNTING_UNIT_ID")+")"+"</option>");
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
                    Accounting For Old Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code_old" id="cmbOffice_code_old" tabindex="2" >
                       <%
                   System.out.println("welcome");
                   System.out.println(oid+"  " +oname+"   "+office_id);
                   ResultSet rs1=null,rs11=null;
                try
                {
                ps=con.prepareStatement("select distinct OLD_OFFICE_ID from PMS_DCB_MST_BENEFICIARY where OFFICE_ID=?");
                ps.setInt(1,office_id);
                rs1=ps.executeQuery();
                
                while(rs1.next())
                        {
                        ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                        ps2.setInt(1,rs1.getInt("OLD_OFFICE_ID"));
                        rs11=ps2.executeQuery();
                        
                        while (rs11.next())
                            {
                              out.println("<option value="+rs1.getInt("OLD_OFFICE_ID")+">"+rs11.getString("OFFICE_NAME")+"("+rs1.getInt("OLD_OFFICE_ID")+")"+"</option>");                           
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
                    Cheque Dishonour Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                 <td>
                  <div align="left">
                  <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this)"/>
                    <!--  <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmCheque_Dishonour.txtCrea_date,1);"
                         alt="Show Calendar"></img> -->
                                    
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
           <tr align="left" id="optionid" style="display:none">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5">
         
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
                    Receipt  Number  
                                         
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
                  <div align="left">Reason for dishonoring</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="60"  tabindex="9" 
                                rows="3"></textarea>
                  </div>
                </td>
              </tr>
              <tr class="table">
              
                <td>
                  <div align="left">
                   Recieved Fresh Cheque/DD     <font color="#ff2121">* </font>
                          </div>
                </td>
                <td>
                  
                    <input type="radio" name="txtCheque_DD2" id="txtCheque_DD2"
                            value="Y"  onclick="cheque(this.value)">Yes
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtCheque_DD2" id="txtCheque_DD2" checked
                           value="N" onclick="cheque(this.value)">No &nbsp;&nbsp;&nbsp;&nbsp; 
                    
         
                </td>
                         
                </tr>
                </table>
                <div align="left" id="div_cheque" style="display:none" >
             <table cellspacing="1" cellpadding="2" border="1" width="100%"> 
             <tr class="table">
             <td>
                  Created Fresh Receipt </td>
                  <td>
                  
                    <input type="radio" name="txtCheque_DD3" id="txtCheque_DD3" tabindex="15"
                            value="Y"   onclick="check_amount(this.value),freshreceiptclick(this.value)">Yes
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtCheque_DD3" id="txtCheque_DD3" onclick="check_amount(this.value),freshreceiptclick(this.value)"
                           value="N"  >No &nbsp;&nbsp;&nbsp;&nbsp; 
                           
                           <input type="button" id="viewbtn" name="viewbtn" value="View" onclick="ListCheq();"/>
         
                </td>
             </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    if Yes Received Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date2" id="txtCrea_date2" tabindex="5" 
                           maxlength="10" size="11" 
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="Check_Date(this.value);return checkdt(this)"
                           ></input>
                           
                           
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmCheque_Dishonour.txtCrea_date2,1);"
                         alt="Show Calendar"></img> 
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
                  
                    <input type="radio" name="txtCheq_DD_Issued" id="txtCheq_DD_Issued" checked="checked" 
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
                    Fresh Cheque/DD Number & Date
                    <font color="#ff2121"> * </font>
                   </div>
                </td>
                
                    <td width="74%">
                    <div align="left">
                    <input type="text" name="txtCheque_DD_NO2" maxlength="10"  onkeypress="return numbersonly(event)"  
                           id="txtCheque_DD_NO2" onchange="check_oldcheqno(this,event)" onblur="check_dd_cheque();" size="11"/>
                    <input type="text" name="txtCheque_DD_date2" id="txtCheque_DD_date2" 
                           maxlength="10" size="11"    
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="check_currentdate(this);Check_Date(this.value);check(this);return checkdt(this)"
                           />
                    <!--input type="text" name="txtCheque_DD_date2" id="txtCheque_DD_date2" 
                           maxlength="10" size="11"    
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return check(this)"
                           /-->       
                    
                  </div>
                
                </td>
              
              </tr>
          
             
              <tr class="table">
               
                <td width="26%">
                  <div align="left">
                    New Cheque Amount (Rs. P.) 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="74%">
                  <div align="left">
                    <input type="text" name="txtAmount2"  onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);" tabindex="10" 
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
                <input type="SUBMIT" name="butSub" id="butSub" value="SUBMIT" disabled/>
               
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="call_clr();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="exit();"/>
              </div>
            </td>
          </tr>
        </table>
    
    </form></body>
</html>