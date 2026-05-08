<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Payment System</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../scripts/BankPay_PendingBill_Adj_memo_Cancel.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"         media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet'         media='screen'/>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
    <!-- to avoid future date the above script used-->
    <script type="text/javascript" src="../../../../Security/scripts/tabpane.js">  </script>
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
            document.frmBankPay_PendingBill_Cancel.txtCrea_date.value=day+"/"+month+"/"+year;
            doFunction('load_Voucher_No','null');
        }
</script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="call_clr();loadDate();foc()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%">
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Cancel Bank Payment for Pending Bills </font>
          </div>
        </td>
      </tr>
    </table>
    <form name="frmBankPay_PendingBill_Cancel" id="frmBankPay_PendingBill_Cancel"
                  method="POST"
                  action="../../../../../BankPay_PendingBill_Adj_memo_Cancel.view?Command=Cancel"
                  onsubmit="return checkNull()">
      <input type="hidden" id="jour_hid" name="jour_hid" value="">
         <input type="hidden" id="Count_hid" name="Count_hid" value="">
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
                  <div align="left">Office Name</div>
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
                     
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                            tabindex="1">
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                            tabindex="2">
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
                        //ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('NC','CL','RD')");
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
                           onblur="call_date(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmBankPay_PendingBill_Cancel.txtCrea_date,1);"
                         alt="Show Calendar"></img> 
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Payment&nbsp;Voucher Number</div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="txtVoucher_No" id="txtVoucher_No"
                            tabindex="5"
                            onchange="doFunction('load_Voucher_Details','null');">
                      <option value="">--Select Voucher Number--</option>
                    </select>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Operation A/c Code</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCash_Acc_code"
                           id="txtCash_Acc_code" 
                           style="background-color: #ececec" readonly="readonly"
                           maxlength="8" size="9"/>
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
                    <input type="text" name="txtBankAccountNo"
                           onkeypress="return numbersonly(event)" id="txtBankAccountNo"
                           maxlength="15" size="15"
                           style="background-color: #ececec"
                           readonly="readonly"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Bank Name</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBankName" id="txtBankName"
                           style="background-color: #ececec" readonly="readonly"
                           size="50" maxlength="49"/>
                     
                    <input type="hidden" name="txtBankId" id="txtBankId"
                           size="5" maxlength="5"/>
                     
                    <input type="hidden" name="txtBranchId" id="txtBranchId"
                           size="5" maxlength="5"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">CR/DR Indicator</div>
                </td>
                <td>
                  <div align="left">
                    <input type="hidden" name="txtCR_DB" id="txtCR_DB"
                           value="CR" size="2"/>
                     
                    <input type="text" name="txtCR_DB_desc"
                           style="background-color: #ececec" readonly="readonly"
                           id="txtCR_DB_desc" value="CREDIT" size="6"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Type</div>
                </td>
                <td width="60%">
                  <div align="left">
                     <input type="text" name="cmbMas_SL_type" tabindex="9"  style="background-color: #ececec"  readonly="readonly"  
                                id="cmbMas_SL_type"  size="50"/>
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
                           <input type="text" name="cmbMas_SL_Code" tabindex="9"  style="background-color: #ececec"  readonly="readonly"  
                                    id="cmbMas_SL_Code"  size="50"/>
                        </div>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Paid To 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtPaid_to" id="txtPaid_to" size="50" style="background-color: #ececec"  readonly="readonly" />
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Voucher for the Part Amount of the Cheque Value 
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="radPart_Amt" id="radPart_Amt"
                           checked="checked" value="Y"/>YES &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="radPart_Amt" id="radPart_Amt"
                           value="N"/>NO
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Part Amount (Rs. P.) 
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtPart_Amount" id="txtPart_Amount"
                           style="background-color: #ececec"  readonly="readonly" 
                           onkeypress="return limit_amt(this,event);"
                           tabindex="8" onblur="valid_amt(this);" 
                           maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50"
                              tabindex="6"
                              style="background-color: #ececec"  readonly="readonly"
                              onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
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
                    <input type="text" name="txtAmount"
                           onkeypress="return limit_amt(this,event);"
                           style="background-color: #ececec"  readonly="readonly"
                           tabindex="8" onblur="valid_amt(this);" id="txtAmount"
                           maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </div>
         
        <div class="tab-page" id="gd">
          <h2 class="tab">Details</h2>
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis">
                 
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="table">
                        <th>Voucher No.</th>
                        <th>Voucher Date</th>
                        <th>Account Head Code </th>
                        <th>Sub Ledger Type</th>
                        <th>Sub Ledger Code</th>
                        <th>Amount</th>
                      </tr>
                       <tbody id="grid_body"  name="grid_body" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
      <br></br>
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