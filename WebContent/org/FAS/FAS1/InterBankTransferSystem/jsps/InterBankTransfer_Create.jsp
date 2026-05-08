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
    <title>Inter Bank Transfer System</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
   
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/PaymentSystem/scripts/Common_PaymentType.js"></script>
    <script type="text/javascript" src="../scripts/InterBankTransfer_Create.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
    <!-- to avoid future date the above script used-->
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">
          </script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script>     
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Date_Check.js"></script>    
          
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_FT_forIBT.js"></script> 
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
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
                document.frmInterBankTransfer_Create.txtCrea_date.value=day+"/"+month+"/"+year;
                call_date(document.frmInterBankTransfer_Create.txtCrea_date);  
       }
</script>
  </head>
  <body onload="call_clr();LoadAccountingUnitID_Create('LIST_ALL_UNITS');setTimeout('loadDate()', 300);foc()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Inter Bank Transfer System (Create)</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmInterBankTransfer_Create" id="frmInterBankTransfer_Create" method="POST"
                  action="../../../../../InterBankTransfer_Create.view?Command=Add"
                  onsubmit="return checkNull()">
     <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  
           
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
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onChange="common_LoadOffice_New(this.value);">
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" >
                      
                      <%
                      int old_offid=0;
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
                           
                           
                           ps2=con.prepareStatement("select old_office_id from hrm_emp_current_posting where employee_id=? and old_office_id is not null and Date_Allowed_Upto>=?");
                             ps2.setInt(1,empid);
                             ps2.setDate(2, ctdate);
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
                           maxlength="10" size="11"  readonly
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);dateCheck(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmInterBankTransfer_Create.txtCrea_date,1);"
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
                   From Bank Account Number
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  <!--  <input type="text" name="txtBankAccountNo"  onkeypress="return numbersonly(event)" onchange="doFunction('load_bank_deatils','CR');"
                           id="txtBankAccountNo" maxlength="15"  size="15" />-->
                    <select name="txtBankAccountNo" id="txtBankAccountNo" onchange="doFunction('load_bank_deatils','CR');">
                    <option value="">--Select Bank Account Number--</option>
                    <%
                       
                       //PreparedStatement ps_acc=con.prepareStatement("select b.BANK_AC_NO, a.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID || '-' || b.BANK_AC_NO as BANK_AC_NO_DISPLAY from FAS_MST_BANKS a,FAS_OFFICE_BANK_AC_CURRENT b where b.STATUS='Y' and b.BANK_ID=a.BANK_ID and ACCOUNTING_UNIT_ID=? and  MODULE_ID=? and  CR_DR_TYPE=? order by a.BANK_SHORT_NAME");
                       PreparedStatement ps_acc=con.prepareStatement("select b.BANK_AC_NO,br.branch_id,case when b.BANK_AC_NO in ('42057477791','110128662919', '110128675743' ) then A.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID ||'SNA'||'-'|| b.BANK_AC_NO || '-' || br.branch_name || '-' || br.branch_id " +
                    			" else " + 
                    			" A.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID || '-' || b.BANK_AC_NO || '-' || br.branch_name || '-' || br.branch_id " +
                    			" end as  BANK_AC_NO_DISPLAY   from FAS_MST_BANKS a,FAS_MST_BANK_BRANCHES br ,FAS_OFFICE_BANK_AC_CURRENT b where b.STATUS='Y' and b.BANK_ID=a.BANK_ID and b.branch_id=br.branch_id AND br.BANK_ID=a.BANK_ID and ACCOUNTING_UNIT_ID=? and  MODULE_ID=? and  CR_DR_TYPE=? order by  br.branch_name");
                        ps_acc.setInt(1,unitid);
                        ps_acc.setString(2,"MF010");
                        ps_acc.setString(3,"CR");
                       ResultSet rs_acc=ps_acc.executeQuery();
                       int branch_id=0;
                        while(rs_acc.next())
                        {
                            out.println("<option value="+rs_acc.getLong("BANK_AC_NO")+">"+rs_acc.getString("BANK_AC_NO_DISPLAY")+"</OPTION>");
                            branch_id=rs_acc.getInt("branch_id");
                        }
                        rs_acc.close();
                        ps_acc.close();
                        
                    %>
                    
                    </select>
                   
                  </div>
                </td>
              </tr>
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
                    <input type="text" name="txtCash_Acc_code" id="txtCash_Acc_code" 
                          style="background-color: #ececec"  readonly="readonly" maxlength="8" size="9"/>
                  <!--  <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountNumberList"
                             onclick="MainAccNopopup();"></img>-->
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">
                    From Bank Name
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBankName" id="txtBankName" readonly="readonly" 
                    style="background-color: #ececec"  size="50" maxlength="49"/>
                   <input type="hidden" name="txtBankId" readonly="readonly"
                           id="txtBankId" size="5" maxlength="5"/>
                   <input type="hidden" name="txtBranchId"  readonly="readonly"
                           id="txtBranchId" size="5" maxlength="5"/>
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
                    <input type="text" name="txtAmount"  onkeypress="return limit_amt(this,event);"  onblur="valid_amt(this);"
                           id="txtAmount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
          <tr class="table">
                <td>
                  <div align="left">
                   To Bank Account Number  <font color="#ff2121">*</font>
                  
                  </div>
                </td>
                <td>
                  <div align="left">
                    <!--<input type="text" name="txtSubBankAccountNo" onkeypress="return numbersonly(event)" onchange="doFunction('load_bank_deatils','DR');"
                           id="txtSubBankAccountNo" maxlength="15"  size="15"   /> -->
                     <select name="txtSubBankAccountNo" id="txtSubBankAccountNo" onchange="doFunction('load_bank_deatils','DR');">
                     <option value="">--Select Bank Account Number--</option>
                    <%
                       
                       //PreparedStatement ps_acc=con.prepareStatement("select b.BANK_AC_NO, a.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID || '-' || b.BANK_AC_NO as BANK_AC_NO_DISPLAY from FAS_MST_BANKS a,FAS_OFFICE_BANK_AC_CURRENT b where b.STATUS='Y' and b.BANK_ID=a.BANK_ID and ACCOUNTING_UNIT_ID=? and  MODULE_ID=? and  CR_DR_TYPE=? order by a.BANK_SHORT_NAME");
                        
                        ps_acc=con.prepareStatement("select b.BANK_AC_NO,br.branch_id,case when b.BANK_AC_NO in ('42057477791','110128662919', '110128675743' ) then A.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID ||'SNA'||'-'|| b.BANK_AC_NO || '-' || br.branch_name || '-' || br.branch_id " +
                    			" else " + 
                    			" A.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID || '-' || b.BANK_AC_NO || '-' || br.branch_name || '-' || br.branch_id " +
                    			" end as  BANK_AC_NO_DISPLAY   from FAS_MST_BANKS a,FAS_MST_BANK_BRANCHES br ,FAS_OFFICE_BANK_AC_CURRENT b where b.STATUS='Y' and b.BANK_ID=a.BANK_ID and b.branch_id=br.branch_id AND br.BANK_ID=a.BANK_ID and ACCOUNTING_UNIT_ID=? and  MODULE_ID=? and  CR_DR_TYPE=? order by  br.branch_name");
                    
                        
                        ps_acc.setInt(1,unitid);
                        ps_acc.setString(2,"MF010");
                        ps_acc.setString(3,"DR");
                        rs_acc=ps_acc.executeQuery();
                        branch_id=0;
                        while(rs_acc.next())
                        {
                            out.println("<option value="+rs_acc.getLong("BANK_AC_NO")+">"+rs_acc.getString("BANK_AC_NO_DISPLAY")+"</OPTION>");
                            branch_id=rs_acc.getInt("branch_id");
                        }
                        rs_acc.close();
                        ps_acc.close();
                        
                    %>
                    
                    
                    
                     </select>
                  </div>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">
                            Debit A/c  Code   <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtDebitAccCode" 
                           id="txtDebitAccCode" maxlength="8" style="background-color: #ececec"  readonly="readonly" 
                           onkeypress="return numbersonly(event)"
                           size="9"/>
                 <!--      <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="AccountNumberList"
                             onclick="SubAccNopopup();"></img>  -->
                  </div>
                </td>
              </tr>
             
       
               <tr class="table">
                <td>
                  <div align="left">
                    To Bank Name
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtSubBankName" id="txtSubBankName"  
                    style="background-color: #ececec"  readonly="readonly" size="50" maxlength="49"/>
                   <input type="hidden" name="txtSubBankId" 
                           id="txtSubBankId" size="5" maxlength="5"/>
                   <input type="hidden" name="txtSubBranchId"  
                           id="txtSubBranchId" size="5" maxlength="5"/>
                  </div>
                </td>
              </tr>
                <tr class="table">
                <td>
                  <div align="left">
                    Cheque/DD     <font color="#ff2121"> </font>
                          </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD"
                           checked="checked" value="C"/>Cheque
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD"
                           value="D"/>DD &nbsp;&nbsp;&nbsp;&nbsp;            
                           
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD"
                           value="E" />ECS  
                    
                  </div>
                </td>
              </tr>        
               <tr class="table">
                <td>
                  <div align="left">
                    Cheque/DD Number & Date
                   </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCheque_DD_NO" maxlength="10" onkeypress="return numbersonly(event)"
                           id="txtCheque_DD_NO"  onblur="check_dd_cheque()" size="11"/>
                    <input type="text" name="txtCheque_DD_date" id="txtCheque_DD_date"  
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmInterBankTransfer_Create.txtCheque_DD_date,0,2);"                          
                         alt="Show Calendar"></img>
                         
                         
                  </div>
                </td>
              </tr>
                 <tr class="table">
                <td>
                  <div align="left">
                      Head Office Reference Number
                  
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
                      Head Office Reference Date
                   
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
                         onclick="showCalendarControl(document.frmInterBankTransfer_Create.txtReferenceDate);"
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
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
             <div align="center" style="disply:block" id="newDiv" name="newDiv">
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