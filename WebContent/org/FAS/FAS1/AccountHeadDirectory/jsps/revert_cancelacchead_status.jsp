<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    
    <title>Account Head Directory Maintenance System</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Delete_Acc_Head_Dir_Sys.js"></script>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" language="javascript">
         function foc()
         {document.getElementById("txtAcc_HeadCode").focus();}
    </script>
    
  </head>
  <body onload="call_clr();foc()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <h3><strong>Modification of Account Head Usage Status</strong></h3>
          </div>
        </td>
      </tr>
    </table>
    <form name="FasAcc_Headform_Delete" id="FasAcc_Headform_Delete" method="POST"
                  action="../../../../../Delete_Acc_Head_Dir_Sys.view?Command=Revert"
                  onsubmit="return checkNull()">
      <%
  
  Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

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
    //int empid=1015;
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
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >Account Head Details</h2>
           
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
                    <strong>Account Head  Details</strong>
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
                            onchange="sixdigit();" 
                            onblur="doFunction('checkCode1','null');"  size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Account&nbsp;Head&nbsp;Description
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadDesc"
                           id="txtAcc_HeadDesc" maxlength="125" size="80"  readonly="readonly"
                           class="disab"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    A/c Head&nbsp;Major&nbsp;Group 
                  </div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="txtMajor_id"
                           id="txtMajor_id" maxlength="50" size="50"  readonly="readonly"
                           class="disab"/>
                    
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    A/c Head&nbsp;Minor&nbsp;Group 
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" name="txtMinor_id"
                           id="txtMinor_id" maxlength="50" size="50"  readonly="readonly"
                           class="disab"/>
                    
                    
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Sub-Group-1</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" name="txtProg_id"
                           id="txtProg_id" maxlength="50" size="50"  readonly="readonly"
                           class="disab"/>
                    
                    
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Sub-Group-2</div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="txtProg_sub_id"
                           id="txtProg_sub_id" maxlength="50" size="50"  readonly="readonly"
                           class="disab"/>
                    
                    
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Date of Creation 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date"
                           maxlength="10" size="11"  readonly="readonly"
                           class="disab" />
                     
                    
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Balance Type 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="txtBal_type" id="txtBal_type"
                           checked="checked" value="CR"/>Credit
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtBal_type" id="txtBal_type"
                           value="DR"/>Debit &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtBal_type" id="txtBal_type"
                           value=""/>Credit/Debit
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    Nature 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="txtNature" id="txtNature"
                           checked="checked" value="C"/>Collection
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtNature" id="txtNature"
                           value="O"/>Operation &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtNature" id="txtNature"
                           value="R"/>Regular
                  </div>
                </td>
              </tr> 
              <tr class="table">
                <td>
                  <div align="left">
                    A/c&nbsp;Head&nbsp;Code&nbsp;currently&nbsp;in Use&nbsp;? 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="txtUse_status" id="txtUse_status"
                           onclick="enableUsage(this.value);" value="Y"/>YES
                                                                         &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtUse_status" id="txtUse_status"
                           onclick="enableUsage(this.value);" value="N"
                           checked="checked"/>NO
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Date&nbsp;Ineffective&nbsp;from ?
                   <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtlast_date" id="txtlast_date" class="tdTitle"
                           maxlength="10" size="11" 
                          onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.FasAcc_Headform_Delete.txtlast_date);"
                         alt="Show Calendar"></img>
                    
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">File Reference Number & Date
                  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtRef_no" id="txtRef_no" class="tdTitle"
                           maxlength="50" size="55"  />
                    
                    <input type="text" name="txtRef_date" id="txtRef_date" class="tdTitle"
                           maxlength="10" size="11" 
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.FasAcc_Headform_Delete.txtRef_date);"
                         alt="Show Calendar"></img>
                           
                    
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                    TB Mandatory ? 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="txtTB_mandatory" checked="checked" 
                           id="txtTB_mandatory" value="Y"/>YES
                                                           &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtTB_mandatory"
                           id="txtTB_mandatory" value="N"/>NO
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Access&nbsp;Restricted 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="txtaccess" id="txtaccess"
                           onclick="enableOffice(this.value);" value="Y"/>YES
                                                                          &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtaccess" id="txtaccess"
                           onclick="enableOffice(this.value);" value="N"
                           checked="checked"/>NO
                  </div>
                </td>
              </tr>
            
              <tr class="table">
                <td>
                  <div align="left">
                    Accessible only by
                  </div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="txtApp_offid"
                           id="txtApp_offid" maxlength="50" size="50"  readonly="readonly"
                           class="disab"/>
                                      
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">Work Nature</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" name="txtApp_for_workid" id="txtApp_for_workid"
                           maxlength="60" size="60" readonly="readonly"
                           class="disab"/>
                    
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">A/c Head Access Applicable Wing Name</div>
                </td>
                <td>
                  <div align="left">
                  <input type="text" name="txtApp_wingId"
                           id="txtApp_wingId" maxlength="50" size="50"  readonly="readonly"
                           class="disab"/>
                  </div>
                </td>
              </tr>
<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->             
              <tr class="table">
                <td>
                  <div align="left">
                    Sub&nbsp;Ledgers&nbsp;Mandatory
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                   
                    <input type="radio" name="txtsub_ledger_man_YN"
                           id="txtsub_ledger_man_YN"
                           onclick="sub_Ledger_man(this.value);" value="Y"/>YES
                 
                       &nbsp;&nbsp;&nbsp;&nbsp; 
                      
                    <input type="radio" name="txtsub_ledger_man_YN"
                           id="txtsub_ledger_man_YN" checked="checked"
                           onclick="sub_Ledger_man(this.value);" value="N"/>NO
                 
                  </div>
                </td>
              </tr>
<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->              
                <tr class="table">
                <td>
                  <div align="left">
                    Sub&nbsp;Ledgers&nbsp;Applicable ? 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                   <table border="0">
                    <tr> 
                     <td>
                     <div id="yes_applicable">
                        <input type="radio" name="txtsub_ledger_YN" 
                               id="txtsub_ledger_YN"
                               onclick="enableSub_Ledger(this.value);" 
                               value="Y"/>YES
                        &nbsp;&nbsp;&nbsp;&nbsp;
                     </div>   
                     </td>
                     <td>
                     <div id="no_applicalbe">
                        <input type="radio" name="txtsub_ledger_YN"
                               id="txtsub_ledger_YN"  
                               checked="checked"
                               onclick="enableSub_Ledger(this.value);" 
                               value="N"/>NO
                      </div>     
                      </td>
                      </tr>
                    </table>       
                  </div>
                </td>
              </tr>
<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->             
              <tr class="table">
                <td>
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50"
                              rows="4"  readonly="readonly"
                           class="disab"></textarea>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </div>
         
         
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <!---my edit -->
                  <div id="sub_ledge_dis" style="display:none">
                  </div>
                  <div id="grid" style="display:none">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="tdTitle">
                        <td colspan="2">
                          <div align="left">
                            <strong>Existing Applicable Sub-Ledger Types </strong>
                          </div>
                        </td>
                      </tr>
                      <tr class="table">
                        
                        <th width="30%">Sub-Ledger Type Code</th>
                        <th width="70%">Sub-Ledger Type Description</th>
                      </tr>
                       <tbody id="grid_body" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </div>
     </br>
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