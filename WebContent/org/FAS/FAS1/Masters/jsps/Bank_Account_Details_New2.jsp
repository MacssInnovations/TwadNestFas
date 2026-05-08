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
    <title>Bank Accounting System</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>   
    <script type="text/javascript" src="../scripts/Bank_Account_Details_New2.js"></script>
    
    
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
   
    <script type="text/javascript" src="../scripts/CalendarControl_New.js"></script> 
    
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
       
        document.frmBank_AccountHeadCode_Module.txtCB_Year.value=year;
        document.frmBank_AccountHeadCode_Module.txtCB_Month.value=month;
        
         }
         function getLastDateOfMonth(){
        	 //var lastdateee = new Date();
        	var Month = document.frmBank_AccountHeadCode_Module.txtCB_Month.value;
        	var Year = document.frmBank_AccountHeadCode_Module.txtCB_Year.value;
        	 
        	//alert("Month & Year ***"+Month + "" +Year);
        	var lastdateee=new Date((new Date(Year, Month,1))-1);
        	 var lastday = lastdateee.getDate();
        	 
             var lastmonth = lastdateee.getMonth();
            
             lastmonth= lastmonth+1;
           
             var lastyear = lastdateee.getFullYear();
             
        	 
        	 
        	// alert("Last date **********"+lastday+"/"+lastmonth+"/"+lastyear);
        	 if(lastmonth<10)
        		 {
        		 var lastD = lastday+"/0"+lastmonth+"/"+lastyear;
        		 document.frmBank_AccountHeadCode_Module.txtverify_date.value =lastD;
        		 }
        	 else
        		 {
        		 var lastD1 = lastday+"/"+lastmonth+"/"+lastyear;
        		 document.frmBank_AccountHeadCode_Module.txtverify_date.value = lastD1;
        		 }
        	 }

</script>
  </head>
  <body onload="loadyear_month();ClearAll();LoadAccountingUnitID('LIST_ALL_UNITS');" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">
              <strong><strong>Bank Balance Monitoring System</strong></strong>
            </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmBank_AccountHeadCode_Module" id="frmBank_AccountHeadCode_Module"  >
        <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
double accno1=0.0;
String accno2="";
long accno=0;
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"  tabindex="1">
                 
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
                                      ps.close();
                                      rs.close();                                        
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
                                          out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                          unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                          ps.close();
                                          rs.close();
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
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)" onblur="ClearAll()" >
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onchange="getLastDateOfMonth();">        
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
                    <select name="txtBankAccountNo" id="txtBankAccountNo" onchange="ClearAll();doFunction('loadbankdeatils',this.value);getLastDateOfMonth();"   >  
                    <option>Select bank a/c number</option> 
                    <%
                        ps2=con.prepareStatement("select BANK_AC_NO,a.BANK_SHORT_NAME||'-'||BANK_AC_TYPE_ID||'-'||bank_ac_no||'-'||AC_OPERATIONAL_MODE_ID as bankacno_type from FAS_MST_BANK_BALANCE b,FAS_MST_BANKS a where accounting_unit_id=? and b.bank_id = a.bank_id  order by a.BANK_SHORT_NAME");
                        ps2.setInt(1,unitid);
                   
                        results2=ps2.executeQuery();                        
                        while(results2.next())
                        {
                            accno=results2.getLong("bank_ac_no");
                            accno2=results2.getString("bankacno_type");
                            out.println("<option value="+accno+">"+accno2+"</option>");                            
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
                     <input type="hidden" name="txtOperation_mode" id="txtOperation_mode" size="50" readonly="readonly" class="disab" />
                     <input type="text" name="txtOperation_mode_name" id="txtOperation_mode_name" size="50" readonly="readonly" class="disab" />
                  </div>
                </td>
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
                           onblur="return checkdt(this);" readonly class="disab" />                    
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
                           id="txtDepositAmount" maxlength="17" size="18" readonly class="disab" />
                  </div>
                </td>
              </tr>
              
              <tr class="table">
               <td align="left">To Date</td>
               <td align="left">
                   <input type="text" name="txtverify_date" id="txtverify_date" maxlength="10"  size ="11"
                          onblur="doFunction('Collection',this.value);" readonly class="disab">
                 <!--    <img src="../../../../../images/calendr3.gif"
                          onclick="showCalendarControl(document.frmBank_AccountHeadCode_Module.txtverify_date);callblur()"  
                          alt="Show Calendar" ></img>  -->
               </td>
              </tr>
                 
                <tr class="table">
                <td>
                  <div align="left">
                    Opening Balance Amount (Rs. P.) as per PassBook 
                    
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBalanceAmount"  onkeypress="return limit_amt(this,event);" tabindex="9" onblur="valid_amt(this);"
                           id="txtBalanceAmount" size="25"  />
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    Opening Balance Amount (Rs. P.) as per Cash Book 
                    
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtOB_Remittance"  onkeypress="return limit_amt(this,event);" tabindex="10" onblur="valid_amt(this);"
                           id="txtOB_Remittance" size="25"  />
                  </div>
                </td>
              </tr>
              
          
             
         <tr class="table">
                <td align="left">Collection</td>
                <td align="left">
                    Cash Receipt &nbsp; &nbsp;<input type="text" name="txtColCR" id="txtColCR" size="20" class="disab" readonly="readonly" ><br>
                    Bank Receipt &nbsp; <input type="text" name="txtColBR" id="txtColBR" size="20" class="disab" readonly="readonly" ><br>
                    Fund Receipt &nbsp; <input type="text" name="txtColFR" id="txtColFR" size="20" class="disab"  readonly="readonly" ><br>
                </td>
         </tr>
           
         <tr class="table">
               <td align="left">Remittance</td>
               <td align="left">
                  <input type="text" name="txtRemittance" id="txtRemittance" size="20" class="disab" readonly="readonly" >
               </td>
         </tr>
        
           
        <tr class="table">
            <td align="left">Withdrawls</td>
            <td align="left">
                <input type="text" name="txtWithdrawl" id="txtWithdrawl" size="20" class="disab" readonly="readonly">      
            </td>
        </tr>
        
        <tr class="table">
            <td align="left">Closing Balance as per CashBook</td>
            <td align="left">
                <input type="text" name="txtClosing_Bal_Collection" id="txtClosing_Bal_Collection" size="20" class="disab" readonly="readonly">                
            </td>
        </tr>
        
        
        <tr class="table">
            <td align="left">Closing Balance as per Pass book</td>
            <td align="left">
                <input type="text" name="txtClosing_Bal_Remittance" id="txtClosing_Bal_Remittance" size="20" class="disab" readonly="readonly">
            </td>
        </tr>
        
        
        <tr class="table">
            <td align="left">Difference in Balance</td>
            <td align="left">
                <input type="text" name="txtDiff_Bal" id="txtDiff_Bal" size="20" class="disab" readonly="readonly">           
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
         
    </form>
  </body>
</html>