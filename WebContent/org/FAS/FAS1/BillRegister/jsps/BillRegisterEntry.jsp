<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %> 
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>BillRegisterEntry</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/> 
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/> 
     <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
      <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
      <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
       <script type="text/javascript" src="../scripts/Common_ReceiptType.js"></script>
      <script type="text/javascript" src="../../Reports/ReceiptSystem/scripts/CalendarControl.js"></script> 
      <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
      <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
      <script type="text/javascript" src="../scripts/BillRegisterEntry.js"></script>
  </head>
        <body onload="callProceeding();"> 
			<form name="billRegisterForm" action="Get">
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
            
                       // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
         %>
         
            <table cellspacing="1" cellpadding="3" width="100%">
                    <tr class="tdH">
                            <td colspan="2">
                                <div align="center">
                                   <font size="4"> Bill Register Entry </font>
                                </div>
                            </td>
                    </tr>
            </table>
            <div class="tab-pane" id="tab-pane-1">
              <div class="tab-page">
                <h2 class="tab" >Part-1</h2>
                <div align="center">
                <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
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
                                <%
                                  int unitid=0;
                                  String unitname="";
                                  try
                                  {
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
                                          out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                          unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                          }
                                          ps.close();
                                          rs.close();
                                      }
                                    }
                                  catch(Exception e)
                                    {
                                    System.out.println("here");
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
                        ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
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
                 
                 <tr>
                        <td class="table" width="40%" align="left">Proceeding No./Certificate No</td>
                        <td class="table" align="left"> 
                            <select name="proceedingno" id="proceedingno" onchange="callProceedingDate(this.value);">
                            <option value="">select</option>
                            </select>
                        </td>
                 </tr>
                 <tr>
                        <td class="table" width="40%" align="left">Proceeding Date</td>
                        <td class="table" align="left"> 
                                   <input type="text" name="proceedingdate" id="proceedingdate" readonly="readonly"/>  
                                   <input type="hidden" name="cashbookyear" id="cashbookyear"/>
                                    <input type="hidden" name="cashbookmonth" id="cashbookmonth"/>
                                 </td>               
                 </tr>
                 <tr>
                        <td class="table" align="left"> Sanctioned Amount(in Rs.)</td>
                        <td class="table" align="left">
                            <input type="text" name="sanctionedamount" id="sanctionedamount" readonly="readonly" />
                        </td>
                 </tr>
                 <tr>
                        <td class="table" align="left"> Payable to</td>
                        <td class="table" align="left">
                            <input type="text" name="paidto" id="paidto" readonly="readonly"/>
                            <input type="hidden" name="payeecode" id="payeecode"/>
                            <input type="hidden" name="billregisterno" id="billregisterno" disabled="disabled"/>
                        </td>
                 </tr>
                 <tr>
                        <td class="table" width="40%" align="left">Bill Date</td>
                        <td class="table" align="left"> 
                                    <input type="text" name="billregisterdate" size="20" maxlength="10" id="billregisterdate" onFocus="javascript:vDateType='3'"
                                    onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.forms[0].billregisterdate);" alt="Show Calendar" ></img> 
                        </td>               
                 </tr>
                 <tr>
                        <td class="table" width="40%" align="left">Bill Processing done by </td>
                            <td width="60%" class="table">
                                 <table align="left">
                                 <tr align="left">
                                  <td>
                                          <div align="left">
                                                <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code" > 
                                                  <option value="">Select Code</option>
                                                </select> 
                                          </div>
                                  </td>
                                  <td>
                                        
                                         <div align="left" id="offlist_div_master" style="display:none">
                                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_master();"></img>
                                            <input type="text" name="txtOfficeID_mas" id="txtOfficeID_mas" maxlength="4" size="5"  onblur="mas_office(this.value);" />
                                          </div>
                                           <div align="left" id="emplist_div_master"  >
                                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_master();"></img>
                                            <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="5" size="5"  onblur="mas_employee(this.value); callAuthority()" />
                                          </div>
                                         <div style="display:none">
                                         <select  name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="6">
                                            <option value=7>--Select Type--</option>                         
                                        </select>
                                         </div>
                                   </td>
                               </tr>
                             </table>
                         </td> 
                 </tr>
                 <tr>
                        <td class="table" align="left">Office</td>
                        <td class="table" align="left">
                            <input type="text" name="office" id="office" readonly="readonly"/>
                        </td>
                 </tr>
                </table>
               </div>
            </div> 
            <div class="tab-page" id="gd" >
                <h2 class="tab" >Part-2</h2>
                <div align="center">
                    <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">   
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
                           id="txtAcc_HeadCode" maxlength="6" 
                            onkeypress="return numbersonly(event)"
                            onchange="sixdigit();" 
                            onblur="doFunction('checkCode','null');financialDate();"  size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="200" size="100"/>
                  </div>
                </td>
              </tr>
                 <tr class="table">
                    <td width="40%">
                      <div align="left">Sub-Ledger Type <font color="#ff2121">*</font> </div>
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
                 <tr>
                        <td class="table" align="left">Budget Provision</td>
                        <td class="table" align="left">
                            <input type="text" name="budgetprovision" id="budgetprovision" onkeypress="return numbersonly(event)" readonly="readonly"/>
                        </td>
                 </tr>
                 <tr>
                        <td class="table" align="left">Budget so far spent</td>
                        <td class="table" align="left">
                            <input type="text" name="budgetspent" id="budgetspent" onkeypress="return numbersonly(event)" readonly="readonly"/>
                        </td>
                 </tr>
                 <tr>
                        <td class="table" align="left">Amount to be deducted for this Bill</td>
                        <td class="table" align="left">
                            <input type="text" name="amountdeducted" id="amountdeducted" onblur="budgetAvail();" onkeypress="return numbersonly(event)"/>
                        </td>
                 </tr>
                 <tr>
                        <td class="table" align="left">Budget available</td>
                        <td class="table" align="left">
                            <input type="text" name="budgetavailable" id="budgetavailable" disabled="disabled" onkeypress="return numbersonly(event)" readonly="readonly"/>
                        </td>
                 </tr>
                 <tr>
                       <td class="table" width="40%" align="left">Remarks</td>
                        <td class="table" align="left"> 
                            <textarea name="remarks" id="remarks" cols="50"
                                tabindex="6" rows="4"></textarea>
                        </td> 
                 </tr>
                </table>
                </div>
              </div>
            </div>
            <div align="center">
           <table cellspacing="1" cellpadding="3" width="100%">
            <tr class="tdH">
                <td  colspan="2">
                    <div align="center"> 
                        <input type="button" name="onadd" value="Add" id="onadd" onclick="add();" />  
                        <input type="button" name="onupdate" value="Update" id="onupdate" onclick="update();" disabled="disabled"/> 
                        <input type="button" name="ondelete" value="Delete" id="ondelete" onclick="deleted();" disabled="disabled"/> 
                        <input type="button" name="onlist" value="List" id="onlist" onclick="listpopup();" />
                        <input type="button" name="onclear" value="ClearAll" id="onclear" onclick="clearAll();" /> 
                        <input type="button" name="oncancel" value="Cancel" id="oncancel" onclick=" self.close();" /> 
                    </div> 
               </td> 
            </tr>
          </table>
        </div>
             </form>
      </body>
</html>