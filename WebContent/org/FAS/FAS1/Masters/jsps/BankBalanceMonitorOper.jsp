<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Accounting System</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <!--script type="text/javascript" src="../scripts/Bank_AccountHeadCode_Module.js"></script-->
      
        <script type="text/javascript" src="../scripts/BankBalanceMonitorOper.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <!--script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script--> 
    <script type="text/javascript"
            src="../scripts/CalendarControl_New.js"></script> 
     <!-- to avoid future date the above script used-->
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
       
        document.frmBank_AccountHeadCode_Module.txtCB_Year.value=year
        document.frmBank_AccountHeadCode_Module.txtCB_Month.value=month;
        //document.frmPhysicalCashBalance.txtverify_date.value=day+"/"+month+"/"+year;
         }
</script>
  </head>
  <body onload="loadyear_month();" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">
              <strong><strong>Bank Balance Montoring System-Operational A/C</strong></strong>
            </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmBank_AccountHeadCode_Module" id="frmBank_AccountHeadCode_Module"  >
                  
  <%
  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
      ResultSet results=null;
      ResultSet results1=null;
      ResultSet results2=null;
      int accno=0;
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
           <% 
       HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
     
    //int empid=9315;
    int  oid=0;
    String oname="",FAS_SU="";
     if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
         FAS_SU="YES";
    else
         FAS_SU="NO";
    try
    {
           
            ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                 }
            results.close();
            ps.close();
            ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                  }
            results.close();
            ps.close();
     /* */      
            System.out.println("off id.. emp id"+oid+".."+empid);     
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
    
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
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
                                      ps.close();
                                      rs.close();
                                          if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
                                          { 
                                             String su="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID!=? order by ACCOUNTING_UNIT_NAME";
                                             ps=con.prepareStatement(su);
                                             ps.setInt(1,unitid);
                                             rs=ps.executeQuery();
                                              while(rs.next())
                                              {
                                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                              }
                                          }
                                  }
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
                                          ps.close();
                                          rs.close();
                                          if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
                                          { 
                                             String su="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID!=? order by ACCOUNTING_UNIT_NAME";
                                             ps=con.prepareStatement(su);
                                             ps.setInt(1,unitid);
                                             rs=ps.executeQuery();
                                              while(rs.next())
                                              {
                                                 out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                              }
                                          }
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
              <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
              
              
             <tr class="table">
                <td>
                  <div align="left">Bank Account Number
                   <font color="#ff2121">*</font>
                   </div>
                </td>
                <td>
                  <div align="left">
                    <!--input type="text" name="txtBankAccountNo" id="txtBankAccountNo" onchange="doFunction('loadbankdeatils',this.value);"
                            onkeypress="return numbersonly(event)" maxlength="15" size="15"/-->
                    <select name="txtBankAccountNo" id="txtBankAccountNo" onchange="doFunction('loadbankdeatils',this.value);"   >  
                    <option>Select bank a/c number</option>
                    <%
                    ps2=con.prepareStatement("select bank_ac_no from FAS_MST_BANK_BALANCE where  AC_OPERATIONAL_MODE_ID='OPR' and accounting_unit_id=?");
                    ps2.setInt(1,unitid);
                   
                    results2=ps2.executeQuery();
                    while(results2.next())
                    {
                    
                    accno=results2.getInt("bank_ac_no");
                    System.out.println("bank ac no....."+accno);
                    out.println("<option value="+accno+">"+accno+"</option>");
                     }
                    
                    %>
                    </select>
                  </div>
                </td>
              </tr>

              <tr class="table">
                <td>
                  <div align="left">
                     Bank Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="hidden" name="txtBankId" id="txtBankId"  readonly="readonly" class="disab" />
                  <input type="text" name="txtBankId_name" id="txtBankId_name" size="50" readonly="readonly" class="disab" />
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Branch Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                  <input type="hidden" name="txtBranchId" id="txtBranchId"  readonly="readonly" class="disab" />
                  <input type="text" name="txtBranchId_name" id="txtBranchId_name" size="50" readonly="readonly" class="disab" />
                
                  </div>
                </td>
              </tr>

             <tr class="table">
                <td>
                  <div align="left">
                     Bank Account Type
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="hidden" name="txtBankAcc_type" id="txtBankAcc_type"  readonly="readonly" class="disab" />
                  <input type="text" name="txtBankAcc_type_name" id="txtBankAcc_type_name" size="50" readonly="readonly" class="disab" />
                  </div>
                </td>
              </tr>
            <tr class="table">
                <td>
                  <div align="left">
                    Operational Mode
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="hidden" name="txtOperation_mode" id="txtOperation_mode"  readonly="readonly" class="disab" />
                  <input type="text" name="txtOperation_mode_name" id="txtOperation_mode_name" size="50" readonly="readonly" class="disab" />
                  </div>
                </td>
              </tr>
              <tr>
            <td align="left">Operational A/C Code</td>
            <td align="left">
            <input type="text" name="txtCol_accode" id="txtCol_accode" size="15" disabled>
            <input type="hidden" name="txtCol_accode1" id="txtCol_accode1" size="15">
            
        </tr>
              
              <tr class="table">
                <td>
                  <div align="left"> Account Opening Date
                   
                   </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtOpening_date" id="txtOpening_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);" readonly/>
                    
                  </div>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">
                    Initial Deposit Amount (Rs. P.) 
                    
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtDepositAmount"  onkeypress="return limit_amt(this,event);" tabindex="7" onblur="valid_amt(this);"
                           id="txtDepositAmount" maxlength="17" size="18" readonly/>
                  </div>
                </td>
              </tr>
                 <tr class="table">
                <td height="18">
                  <div align="left"> Balance as on Date
                  
                   </div>
                </td>
                <td height="18">
                  <div align="left">
                    <input type="text" name="txtBalance_date" id="txtBalance_date"  tabindex="8"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);" readonly/>
                    
                  </div>
                </td>
              </tr>
                <tr class="table">
                <td>
                  <div align="left">
                    Opening Balance Amount (Rs. P.) 
                    
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBalanceAmount"  onkeypress="return limit_amt(this,event);" tabindex="9" onblur="valid_amt(this);"
                           id="txtBalanceAmount" maxlength="17" size="18" readonly/>
                  </div>
                </td>
              </tr>
              <tr>
            <td align="left">To Date</td>
            <td align="left"><input type="text" name="txtverify_date" id="txtverify_date" maxlength="10" onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"  onblur="doFunction('Collection',this.value);" >
            <img src="../../../../../images/calendr3.gif"
                          onclick="showCalendarControl(document.frmBank_AccountHeadCode_Module.txtverify_date);callblur()"  
                           alt="Show Calendar" ></img> 
            </td>
        </tr>
              <!--tr class="table">
                <td>
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" readonly tabindex="10" onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
                </tr-->
                <tr>
            <td align="left">Collection</td>
            <td align="left">
                <input type="text" name="txtCollection" id="txtCollection" size="20">
                <input type="hidden" name="txtCollection1" id="txtCollection1" size="20">
            </td>
        </tr>
        <tr>
            <td align="left">Remittance</td>
            <td align="left">
                <input type="text" name="txtRemittance" id="txtRemittance" size="20">
                <input type="hidden" name="txtRemittance1" id="txtRemittance1" size="20">
            </td>
        </tr>
        <tr>
            <td align="left">Withdrawls</td>
            <td align="left">
                <input type="text" name="txtWithdrawl" id="txtWithdrawl" size="20">
                <input type="hidden" name="txtWithdrawl1" id="txtWithdrawl1" size="20">
            </td>
        </tr>
        <tr>
            <td align="left">Closing Balance as per Collection</td>
            <td align="left">
                <input type="text" name="txtClosing_Bal_Collection" id="txtClosing_Bal_Collection" size="20">
                <input type="hidden" name="txtClosing_Bal_Collection1" id="txtClosing_Bal_Collection1" size="20">
            </td>
        </tr>
        <tr>
            <td align="left">Closing Balance as per Remittance</td>
            <td align="left">
                <input type="text" name="txtClosing_Bal_Remittance" id="txtClosing_Bal_Remittance" size="20">
                <input type="hidden" name="txtClosing_Bal_Remittance1" id="txtClosing_Bal_Remittance1" size="20">
            </td>
        </tr>
        <tr>
            <td align="left">Difference in Balance</td>
            <td align="left">
                <input type="text" name="txtDiff_Bal" id="txtDiff_Bal" size="20">
                <input type="hidden" name="txtDiff_Bal1" id="txtDiff_Bal1" size="20">
            </td>
        </tr>
             <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="OK" id="cmdOk" onclick="closeWindow()" />
                     </td>
                     
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()"/>
                     </td>
                     <td>
                     <input type="button" id="Exit" name="Exit" value="EXIT" onclick="exit()">
                     </td>
                 </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
    </form></body>
</html>