<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>

<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Account Head Directory Maintenance System</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../org/Library/scripts/checkDate.js"></script>
   
    <script type="text/javascript" src="../scripts/Edit_Acc_Head_Dir_Sys.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
          <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
   <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript"
            src="../../../../Security/scripts/tabpane.js">
          </script>
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
            <h3><strong>Modify Account Head </strong></h3>
          </div>
        </td>
      </tr>
    </table>
    <form name="FasAcc_Headform_Edit" id="FasAcc_Headform_Edit" method="POST"
                  action="../../../../../Edit_Acc_Head_Dir_Sys.view?Command=Add"
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

         //   ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
                            onblur="doFunction('checkCode','null');"  size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Account&nbsp;Head&nbsp;Description<font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadDesc" 
                           id="txtAcc_HeadDesc" maxlength="125" size="80"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    A/c Head&nbsp;Major&nbsp;Group 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="txtMajor_id" id="txtMajor_id" 
                            onchange="doFunction('loadMinor',this.value);">
                      <option value="">--Select Major Group--</option>
                      <%
                try
                {
                ps=con.prepareStatement("select MAJOR_HEAD_CODE,MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS");
                rs=ps.executeQuery();
                    while(rs.next())
                    {
                    out.println("<option value="+rs.getString("MAJOR_HEAD_CODE")+">"+rs.getString("MAJOR_HEAD_DESC")+"</option>");
                    }
                } 
                catch(Exception e)
                {
                System.out.println("Exception in Major combo..."+e);
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
                    A/c Head&nbsp;Minor&nbsp;Group 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="txtMinor_id" id="txtMinor_id">
                      <option value="0">--Select Minor Group--</option>
                    </select>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Sub-Group-1</div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="txtProg_id" id="txtProg_id" onchange="doFunction('subgroup','0')" >
                      <option value="0">--Select Group--</option>
                      <%
                try
                {
                ps=con.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS  order by SUB_HEAD_DESC ");
                rs=ps.executeQuery();
                    while(rs.next())
                    {
                    out.println("<option value="+rs.getInt("SUB_HEAD_CODE")+">"+rs.getString("SUB_HEAD_DESC")+"</option>");
                    }
                }
                catch(Exception e)
                {
                System.out.println("Exception in Progrmme combo..."+e);
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
                  <div align="left">Sub-Group-2</div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="txtProg_sub_id" id="txtProg_sub_id" >
                      <option value="0">--Select Sub Group--</option>
                    </select>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Date of Creation 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.FasAcc_Headform_Edit.txtCrea_date);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Balance Type 
                    <font color="#ff2121">*</font>
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
                    <input type="radio" name="txtUse_status" id="txtUse_status"   checked="checked"
                           onclick="enableUsage(this.value);" value="Y"/>YES
                                                                         &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtUse_status" id="txtUse_status"
                           onclick="enableUsage(this.value);" value="N"
                          />NO
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">If Not in use, when used last?</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtlast_date" id="txtlast_date" disabled="disabled"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.FasAcc_Headform_Edit.txtlast_date);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">File Reference Number & Date</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtRef_no" id="txtRef_no" disabled="disabled"
                           maxlength="50" size="55"/>
                     
                    <input type="text" name="txtRef_date" id="txtRef_date" disabled="disabled"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.FasAcc_Headform_Edit.txtRef_date);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    TB Mandatory ? 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="txtTB_mandatory"  checked="checked"
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
                    <font color="#ff2121">*</font>
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
  <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->            
              <tr class="table">
                <td>
                  <div align="left">
                    Accessible only by
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtApp_offid" id="txtApp_offid"
                           disabled="disabled"
                           onchange="doFunction('office',this.value);"
                           onkeypress="return numbersonly1(event,this);"
                           maxlength="4" size="5"/>
                     
                    <img src="../../../../../images/c-lovi.gif" width="20"
                         height="20" alt="OfficeList" onclick="jobpopup();"></img>
                     
                    <input type="text" name="txtApp_OffName" id="txtApp_OffName" style="font-weight:bold"
                           maxlength="60" size="60" readonly="readonly"
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
                 <input type="hidden" name="txtApp_for_workid" id="txtApp_for_workid"
                           maxlength="60" size="60" readonly="readonly"
                           class="disab"/>
                  <input type="text" name="txtApp_for_workDesc" id="txtApp_for_workDesc" style="font-weight:bold"
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
                    <select size="1" name="txtApp_wingId" id="txtApp_wingId"
                            disabled="disabled">
                      <option value="0">--Select Wing--</option>
                    </select>
                  </div>
                </td>
              </tr>
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
              <tr class="table">
                <td>
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" onkeypress="return check_leng(this.value);"
                              rows="4"></textarea>
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
                    <table cellspacing="1" cellpadding="2" border="1"
                           width="100%">
                      <tr class="tdTitle">
                        <td colspan="2">
                          <div align="left">
                            <strong>Sub-Ledger Types</strong>
                          </div>
                        </td>
                      </tr>
                      <tr class="table">
                        <td width="40%">
                          <div align="left">Sub-Ledger Type Code</div>
                        </td>
                        <td width="60%">
                          <div align="left">
                            <input type="text" name="txtSL_code" id="txtSL_code"
                                   readonly="readonly" class="disab"
                                   maxlength="3" size="4"/>
                          </div>
                        </td>
                      </tr>
                      <tr class="table">
                        <td width="40%">
                          <div align="left">Sub-Ledger Type Description</div>
                        </td>
                        <td width="60%">
                          <div align="left">
                            <select size="1" name="txtSL_Desc" id="txtSL_Desc"
                                    onchange="loadSL(this.value);">
                              <option value="0">--Select--</option>
                              <%
                try
                {
                ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES ");
                rs=ps.executeQuery();
                    while(rs.next())
                    {
                    out.println("<option value="+rs.getString("SUB_LEDGER_TYPE_CODE")+">"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</option>");
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
                <td>
                  <div align="left">
                   Status
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="txtstatus" id="txtstatus"   
                            value="Y"/>YES
                                                                         &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtstatus" id="txtstatus"
                            value="N"
                          />NO
                  </div>
                </td>
              </tr>
                      <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="cmdadd" id="cmdadd"
                                 value="ADD" onclick="ADD_GRID()" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate" value="UPDATE"
                                 id="cmdupdate" onclick="update_GRID()"
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
                  <div id="grid" style="display:none">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="tdTitle">
                        <td colspan="3">
                          <div align="left">
                            <strong>Existing Applicable Sub-Ledger Types </strong>
                          </div>
                        </td>
                      </tr>
                      <tr class="table">
                        <th width="10%">Select</th>
                        <th width="30%">Sub-Ledger Type Code</th>
                        <th width="70%">Sub-Ledger Type Description</th>
                        <th width="10%">Status</th>
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