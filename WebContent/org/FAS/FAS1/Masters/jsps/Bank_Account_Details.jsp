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
    <script type="text/javascript" src="../scripts/Bank_Account_Details.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
          <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
 
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
     <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
         function funClosed(ClosedValue){
             //alert(ClosedValue);
            if(ClosedValue=='Y'){
                document.getElementById("CloDiv").style.display="none";
                document.getElementById("txtClosed_date").value="";
            }else  if(ClosedValue=='N')
            {
            	 document.getElementById("CloDiv").style.display="block"; 
            }
         }
</script>
  </head>
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');foc();setTimeout('type()', 900);" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Bank Account Number Details </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmBank_Account_Details" id="frmBank_Account_Details" >
                  
  <%
  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
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
           <% 
       HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
     
    //int empid=9315;
    int  oid=0;
    String oname="";
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
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="Cmb_BankAcc_No();">        
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
                
                    <select size="1" name="cmbBankId" id="cmbBankId" tabindex="2" onchange="doFunction('getBranch',this.value);">
                    <option value="">-- Select Bank Name --</option>
                      <%
                    try
                    {
                     ps=con.prepareStatement("select BANK_ID, BANK_NAME from FAS_MST_BANKS");
                     rs=ps.executeQuery();
                     while(rs.next())
                     {
                        out.println("<option value="+rs.getInt("BANK_ID")+">"+rs.getString("BANK_NAME")+"</option>");
                     }
                        
                    } 
                    catch(Exception e)
                    {
                    System.out.println("Exception in bank combo..."+e);
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
                    Branch Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbBranchId" id="cmbBranchId" tabindex="3">
                    <option value="">-- Select Branch Name --</option> 
                    </select>
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
                
                <select size="1" name="cmbBankAcc_type" id="cmbBankAcc_type" tabindex="4">
                    <option value="">-- Select  Account Type --</option> 
                    </select>
                
                
                   <!--  <select size="1" name="cmbBankAcc_type" id="cmbBankAcc_type" tabindex="4">
                    <option value="">-- Select  Account Type --</option>
                      <%
                    try
                    {
                     ps2=con.prepareStatement("select trim(ACCOUNT_TYPE_ID) as acc_type_id, ACCOUNT_TYPE from FAS_MST_BANK_AC_TYPES");
                     rs2=ps2.executeQuery();
                     while(rs2.next())
                     {
                        //System.out.println("here");
                        out.println("<option value="+rs2.getString("acc_type_id")+">"+rs2.getString("ACCOUNT_TYPE")+"</option>");
                     }
                        
                    } 
                    catch(Exception e)
                    {
                    System.out.println("Exception in Bank Account Type combo..."+e);
                    }
                    finally
                    {
                    rs2.close();
                    ps2.close();
                    }  
                %>
                    </select> -->
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
                    <input type="text" name="txtBankAccountNo" id="txtBankAccountNo" tabindex="5"
                            onkeypress="return numbersonly(event)" maxlength="15" size="15"/>
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
                
                <select size="1" name="cmbOperation_mode" id="cmbOperation_mode" tabindex="6">
                    <option value="">-- Select Operational Mode --</option> 
                    </select>
                
                   <!--   <select size="1" name="cmbOperation_mode" id="cmbOperation_mode" tabindex="6" >
                    <option value="">-- Select Operational Mode --</option> 
                      <%
                    try
                    {
                     ps=con.prepareStatement("select trim(AC_OPERATIONAL_MODE_ID) as Operational_id, AC_OPERATIONAL_MODE from FAS_MST_AC_OPER_MODES");
                     rs=ps.executeQuery();
                     while(rs.next())
                     {
                        out.println("<option value="+rs.getString("Operational_id")+">"+rs.getString("AC_OPERATIONAL_MODE")+"</option>");
                     }
                        
                    } 
                    catch(Exception e)
                    {
                    System.out.println("Exception in Operational combo..."+e);
                    }
                    finally
                    {
                    rs.close();
                    ps.close();
                    }  
                %>
                    </select>-->
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
                           onblur="return checkdt(this);"/>
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmBank_Account_Details.txtOpening_date);"
                         alt="Show Calendar"></img>
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
                           id="txtDepositAmount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
                 <tr class="table">
                <td>
                  <div align="left"> Balance as on Date
                  
                   </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBalance_date" id="txtBalance_date"  tabindex="8"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmBank_Account_Details.txtBalance_date);"
                         alt="Show Calendar"></img>
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
                           id="txtBalanceAmount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Account Status</div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="Account_Staus" id="Account_Staus" onclick="funClosed(this.value);" value="Y" checked />Live 
                    <input type="radio" name="Account_Staus" id="Account_Staus" onclick="funClosed(this.value);" value="N"/>Closed 
                  </div>
                  <div id="CloDiv" style="display: none;">
                  <input type="text" name="txtClosed_date" id="txtClosed_date"  tabindex="8"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmBank_Account_Details.txtClosed_date);"
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
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="10" onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
       
             <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')" tabindex="20"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="doFunction('Update','null')" tabindex="30"/>
                     </td>
                    <td>
                    <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="doFunction('Delete','null')" tabindex="40"/>
                     </td>
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()" tabindex="50"/>
                     </td>
                     <td>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListHeads()" tabindex="60"/>
                     </td>
                       <td>
                     <input type="button" id="Exit" name="Exit" value="EXIT" onclick="exit()" tabindex="70"/>
                     </td>
                 </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
    </form></body>
</html>