<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="Cache-Control" content="No-Cache"/>
    <title> Validation Of Employee Relieval Details</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Edit_Relieval_Details.js"> </script>
    <script type="text/javascript"            src="../../../../../org/Library/scripts/checkDate.js"></script>
   <!-- <script type="text/javascript" src="../scripts/CalendarCtl_Relieval.js"></script>-->
      <link href="../../../../../css/Sample3.css" rel="stylesheet"          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>
     <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
  </head>
  <body  id="bodyid"><form name="EmpRelieval" id="EmpRelieval" method="POST"
              action="../../../../../Edit_Relieval_serv.view?Command=Add&_status=FR"
              onsubmit="return checkValidate_Status();">
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
    int  oid=0;
    String oname="",oadd1="",oadd2="",ocity="",odist="",olid="",owid="";
    String olname=""; 
    String ownature="";
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
            ps=con.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME from COM_MST_OFFICES a "+
            " left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                    oadd1=results.getString("OFFICE_ADDRESS1");
                    oadd2=results.getString("OFFICE_ADDRESS2");
                    ocity=results.getString("CITY_TOWN_NAME");
                    odist=results.getString("DISTRICT_NAME");
                    
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
  
      <div align="center">
        <table cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong> Validation Of Employee Relieval Details</strong>
              </div>
            </td>
          </tr>
          <tr class="tdTitle">
            <td colspan="2">
              <div align="left">
                <strong>Office Details</strong>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Office ID 
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffId" id="txtOffId" maxlength="4"
                       value="<%=oid%>" size="5" class="disab"  readonly="readonly"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Office Name</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffName" id="txtOffName"
                       value="<%=oname%>" maxlength="60" size="60"
                       readonly="readonly" class="disab" />
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Office Address</div>
            </td>
            <td>
              <div align="left">
              <textarea rows="4" cols="40"  name="txtOffAddr" id="txtOffAddr" readonly="readonly"
                class="disab"><%
                String s=null;
                if(oadd1!=null)
                {
                    s=oadd1;
                }
                if(oadd2!=null)
                {
                    s+="\n"+oadd2;
                }
                if(ocity!=null)
                {
                    s+="\n"+ocity;
                }
                if(odist!=null)
                {
                    s+="\n"+odist;
                }
                if(s!=null)
                    out.print(s);   
                                
                %></textarea>
              </div>
            </td>
          </tr>
          <tr class="tdTitle">
            <td colspan="2">
              <div align="left">
                <strong>Employee Details</strong>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Employee ID 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmployeeid" id="txtEmployeeid"
                       maxlength="5" size="6"
                       onkeypress="return numbersonly1(event,this);"
                       onchange="doFunction('loademp','null');"/>
                 
                <img src="../../../../../images/c-lovi.gif" width="20"
                     height="20" alt="empList" onclick="servicepopup();"></img>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Employee Name</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmpName" id="txtEmpName"
                       readonly="readonly" class="disab" maxlength="40"
                       size="40"/>
                       
               
              </div>
            </td>
          </tr>
           <tr class="table">
            <td>
              <div align="left">Designation</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmpDesig" id="txtEmpDesig"
                       readonly="readonly" class="disab" maxlength="40"
                       size="40"/>
                       <input type="hidden" name="txtdesid" id="txtdesid"
                        class="disab" />
                        <input type="hidden" name="txtofficeid" id="txtofficeid"
                        class="disab" />
              </div>
            </td>
          </tr>
 
          <tr class="tdTitle">
            <td colspan="2">
              <div align="left">
                <strong>Relieval Details</strong>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Relieval&nbsp;Serial&nbsp;Number 
                <font color="#ff2121">
                  *
                </font> 
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtRel_SLNO" id="txtRel_SLNO" 
                onfocus="return checkEID();" onkeypress="return numbersonly1(event,this);"
                onchange="doFunction('Validate_loadReason','null');" maxlength="5" size="6"/>
                <img src="../../../../../images/c-lovi.gif" width="20"
                     height="20" alt="RelievalList" onclick="Relievalpopup1();"></img>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Date Of Relieval  
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtDORelieval" id="txtDORelieval"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3'; return "
                       onkeypress="return calins(event,this);"
                       onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                 
                <img src="../../../../../images/calendr3.gif"
                     onclick=" if(checkEID()==true)showCalendarControl(document.EmpRelieval.txtDORelieval);"
                     alt="Show Calendar"></img>
                 
                <input type="radio" name="rad_DORelieval" id="rad_DORelieval"
                       value="FN" checked="checked"    />FN &nbsp;&nbsp;&nbsp;&nbsp; 
                <input type="radio" name="rad_DORelieval" id="rad_DORelieval"
                       value="AN" />AN
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Reason for Relieval 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbReason" id="cmbReason"
                        onchange="enableReason(this.value);" >
                  <option value="">--Select Reason--</option>
                  <%
                try
                {
                ps=con.prepareStatement("select * from HRM_MST_RELIEVAL_REASONS ");
                rs=ps.executeQuery();
                    while(rs.next())
                    {
                    out.println("<option value="+rs.getString("RELIEVAL_REASON_ID")+">"+rs.getString("RELIEVAL_REASON_DESC")+"</option>");
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
              <div align="left">Remarks</div>
            </td>
            <td>
              <div align="left">
                <textarea name="txtRemarks" id="txtRemarks" cols="50" rows="4" onkeypress="return noEnter(event)"></textarea>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Record Status 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbStatus" id="cmbStatus" disabled="disabled">
                  <option value="">--Select Status--</option>
                  <%
                try
                {
                ps=con.prepareStatement("select * from COM_MST_PROCESS_FLOW ");
                rs=ps.executeQuery();
                    while(rs.next())
                    {
                    out.println("<option value="+rs.getString("PROCESS_FLOW_STATUS_ID")+">"+rs.getString("PROCESS_FLOW_STATUS_DESC")+"</option>");
                    }
                }
                catch(Exception e)
                {
                System.out.println("Exception in Status combo..."+e);
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
          <tr align="left" class="tdH">
            <td colspan="2">
              <strong>Specify The Reason With Details</strong>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <!---my edit -->
              <div id="Reason1" style="display:none">
                <table cellspacing="3" cellpadding="2" border="1" width="100%">
                  <tr class="tdTitle">
                    <td colspan="2">
                      <div align="left">
                        <strong>Transfer</strong>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Office
                        Id to which posted 
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtT_OffId" id="txtT_OffId"
                               onkeypress="return numbersonly1(event,this);" onchange="doFunction('office',this.value);"
                               maxlength="4" size="5"/>
                         
                        <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="OfficeList" onclick="jobpopup();"></img>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Office Name</div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtT_OffName" id="txtT_OffName" 
                                maxlength="60" size="60"
                               readonly="readonly" class="disab"/>
                      </div>
                    </td>
                 </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Further
                        Reposting Required 
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="radio" name="radT_Repost" id="radT_Repost"
                               value="Y"/>YES &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input type="radio" name="radT_Repost" id="radT_Repost"
                               value="N" checked="checked"/>NO
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Proceeding
                                        Date </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtT_proc_Date"
                               id="txtT_proc_Date" maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.EmpRelieval.txtT_proc_Date);"
                             alt="Show Calendar"></img>
                      </div>
                    </td>
                  </tr>
                  <!--<tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Proceeding
                                        Number</div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtT_proc_No" id="txtT_proc_No"
                               maxlength="50" size="55"/>
                      </div>
                    </td>
                  </tr>-->
                </table>
              </div>
              <div id="Reason2" style="display:none">
                <table cellspacing="3" cellpadding="2" border="1" width="100%">
                  <tr class="tdTitle">
                    <td colspan="2">
                      <div align="left">
                        <strong>Deputation</strong>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other
                        Department Id 
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                      
                       <select name="txtDept_Id"  id="txtDept_Id" onchange="deptchange()" onfocus="return checkEID();">
                         <option value="">Select the Department</option>
                         
                         <%
                         try
                         {
                         ps=con.prepareStatement("select a.OTHER_DEPT_ID,a.OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS a  order by OTHER_DEPT_NAME");
                        rs=ps.executeQuery();
                        String strcode="";
                        String strname="";           
                        while(rs.next())
                        {
                            strcode=rs.getString("OTHER_DEPT_ID");
                            strname=rs.getString("OTHER_DEPT_NAME");
                            
                            out.println("<option value='"+strcode+"'>"+strname+"</option>");
                            
                         }
                      }
                      catch(Exception e)
                      {
                        System.out.println("Exception in grid.."+e);
                      }
                       finally
                      {
                            rs.close();
                            ps.close();
                            //connection.close();
                      
                      }    
                         
                         %>
                         </select>
                        
                      
                      
                        <input type="hidden" name="txtD_ODep_Id" id="txtD_ODep_Id"
                               readonly="readonly" class="disab" 
                               maxlength="4" size="5" />
                         
                        <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="DepartmentList" onclick="jobpopup();"></img>
                             
                             <input type="hidden" name="txtD_DepName" id="txtD_DepName" 
                                maxlength="60" size="60"
                               readonly="readonly" class="disab"/>
                      </div>
                    </td>
                  </tr>
                <!--  <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other
                        Department Id 
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtD_ODep_Id" id="txtD_ODep_Id"
                               readonly="readonly" class="disab" onkeypress="return numbersonly1(event,this);"
                               maxlength="4" size="5"/>
                         
                        <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="DepartmentList" onclick="jobpopup();"></img>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Department Name</div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtD_DepName" id="txtD_DepName" 
                                maxlength="60" size="60"
                               readonly="readonly" class="disab"/>
                      </div>
                    </td>
                 </tr>-->
                  <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other
                        Department Office Id 
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtD_ODep_OffId"
                               id="txtD_ODep_OffId" readonly="readonly" class="disab"
                               onkeypress="return numbersonly1(event,this);"
                               maxlength="4" size="5"/>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other Department Office Name</div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtD_OffName" id="txtD_OffName" 
                                maxlength="60" size="60"
                               readonly="readonly" class="disab"/>
                      </div>
                    </td>
                 </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Deputation
                                        Period</div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtD_Period" id="txtD_Period"
                               maxlength="20" size="25"/>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date
                                        Of Deputation </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtD_Date" id="txtD_Date"
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.EmpRelieval.txtD_Date);"
                             alt="Show Calendar"></img>
                      </div>
                    </td>
                  </tr>
                </table>
              </div>
              <div id="Reason3" style="display:none">
                <table cellspacing="3" cellpadding="2" border="1" width="100%">
                  <tr class="tdTitle">
                    <td colspan="2">
                      <div align="left">
                        <strong>Promotion</strong>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Office
                        Id to which posted 
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtP_OffId" id="txtP_OffId"
                               onkeypress="return numbersonly1(event,this);" onchange="doFunction('office',this.value);"
                               maxlength="4" size="5"/>
                         
                        <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="OfficeList" onclick="jobpopup();"></img>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Office Name</div>
                    </td>
                    <td>
                      <div align="left">
                      <input type="hidden" name="txtP_depid" id="txtP_depid">
                        <input type="text" name="txtP_OffName" id="txtP_OffName" 
                                maxlength="60" size="60"
                               readonly="readonly" class="disab"/>
                      </div>
                    </td>
                 </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;New
                        Designation Id to be Joined 
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtP_desigId" id="txtP_desigId" onchange="doFunction('desig','null');"
                               onkeypress="return numbersonly1(event,this);"
                               maxlength="3" size="4"/>
                         
                        <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="DesignationList"
                             onclick="desigpopup();"></img>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Designation Name</div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtP_DesigName" id="txtP_DesigName" 
                                maxlength="60" size="60"
                               readonly="readonly" class="disab"/>
                      </div>
                    </td>
                 </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Further
                        Reposting Required 
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="radio" name="radP_Repost" id="radP_Repost"
                               value="Y"/>YES &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input type="radio" name="radP_Repost" id="radP_Repost"
                               value="N" checked="checked"/>NO
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Proceeding
                                        Date </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtP_proc_Date"
                               id="txtP_proc_Date" maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.EmpRelieval.txtP_proc_Date);"
                             alt="Show Calendar"></img>
                      </div>
                    </td>
                  </tr>
                  <!--<tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Proceeding
                                        Number</div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtP_proc_No" id="txtP_proc_No"
                               maxlength="50" size="55"/>
                      </div>
                    </td>
                  </tr>-->
                </table>
              </div>
              <div id="Reason4" style="display:none">
                <table cellspacing="3" cellpadding="2" border="1" width="100%">
                  <tr class="tdTitle">
                    <td colspan="2">
                      <div align="left">
                        <strong>Long Leave</strong>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Leave
                        Type Id 
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbLL_TypeId" id="cmbLL_TypeId">
                          <option value="">--Select Leave Type--</option>
                          <%
                try
                {
                ps=con.prepareStatement("select * from HRM_MST_LEAVE_TYPES ORDER BY LEAVE_TYPE_CODE");
                rs=ps.executeQuery();
                while(rs.next())
                {
                out.println("<option value="+rs.getString("LEAVE_TYPE_CODE")+">"+rs.getString("LEAVE_TYPE_DESC")+"</option>");
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
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Purpose</div>
                    </td>
                    <td>
                      <div align="left">
                        <textarea name="txtLL_purpose" id="txtLL_purpose"
                                  cols="50" rows="4"></textarea>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Period
                                        From </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtL_Period_From"
                               id="txtL_Period_From" maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.EmpRelieval.txtL_Period_From);"
                             alt="Show Calendar"></img>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Period&nbsp;To
                                        </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtL_Period_To"
                               id="txtL_Period_To" maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.EmpRelieval.txtL_Period_To);"
                             alt="Show Calendar"></img>
                      </div>
                    </td>
                  </tr>
                </table>
              </div>
              <div id="Reason5" style="display:none">
                <table cellspacing="3" cellpadding="2" border="1" width="100%">
                  <tr class="tdTitle">
                    <td colspan="2">
                      <div align="left">
                        <strong>Diversion</strong>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Office
                                        Id to which Diverted</div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtDv_OffId" id="txtDv_OffId"
                               onkeypress="return numbersonly1(event,this);" onchange="doFunction('office',this.value);"
                               maxlength="4" size="5"/>
                         
                        <img src="../../../../../images/c-lovi.gif" width="20"
                             height="20" alt="OfficeList" onclick="jobpopup();"></img>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Office Name</div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtDv_OffName" id="txtDv_OffName" 
                                maxlength="60" size="60"
                               readonly="readonly" class="disab"/>
                      </div>
                    </td>
                 </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Up
                                        to Date </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtDv_Date" id="txtDv_Date"
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.EmpRelieval.txtDv_Date);"
                             alt="Show Calendar"></img>
                      </div>
                    </td>
                  </tr>
                </table>
              </div>
           
           
           
             <div id="Reason6" style="display:none">
                <table cellspacing="3" cellpadding="2" border="1" width="100%">
                  <tr class="tdTitle">
                    <td colspan="2">
                      <div align="left">
                        <strong>Study Leave</strong>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Other
                        Institution Name
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                      
                        <input type="text" name="txtInst_Name" id="txtInst_Name"
                                maxlength="60" size="40" />
                      </div>
                    </td>
                  </tr>
                 <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Institution Loaction</div>
                    </td>
                    <td>
                      <div align="left">
                        <textarea name="txtInst_Location" id="txtInst_Location" 
                                rows="4" cols="40"></textarea>
                      </div>
                    </td>
                 </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
                        &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Course Name
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtCourse_Name"
                               id="txtCourse_Name" maxlength="40" size="30"/>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date
                                        From </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtSDate_From" id="txtSDate_From"
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.EmpRelieval.txtSDate_From);"
                             alt="Show Calendar"></img>
                      </div>
                    </td>
                  </tr>
                  
                  <tr class="table">
                    <td>
                      <div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date
                                        To </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtSDate_To" id="txtSDate_To"
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.EmpRelieval.txtSDate_To);"
                             alt="Show Calendar"></img>
                      </div>
                    </td>
                  </tr>
                </table>
              </div>           
            </td>
          </tr>
          
          <tr id="proceed">
         <td colspan="2">         
         <table cellspacing="3" cellpadding="2" border="1" width="100%">  
          <tr align="left" class="tdH">
            <td colspan="2">
              <strong>Reference Order Details</strong>
            </td>
          </tr>
          
          <!--<tr class="table">
            <td colspan="2">
              <div align="left">
                <input type="text" name="txtReason" id="txtReason" maxlength="30"
                       size="30" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Order Details                       
              </div>
            </td>
          </tr>-->
          
          <tr class="table">
            <td>
              <div align="left">
                Ref. Order no.
                
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtRO" id="txtRO" maxlength="15"
                       size="20" readonly="readonly"/>                       
              </div>
            </td>
          </tr>
          
         <tr class="table">
           <td>
              <div align="left">
                Ref. Order Date
                
              </div>
            </td>
          <td>
              <div align="left">
               <input type="text" name="txtRDat" id="txtRDat"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3'; return checkEID() "
                       onkeypress="return calins(event,this);"
                       onblur="if(checkcurdt(this)==true)return checkdt(this);" readonly="readonly"/>
                 
                <!--<img src="../../../../../images/calendr3.gif"
                     onclick=" if(checkEID()==true)showCalendarControl(document.EmpRelieval.txtRDat);"
                     alt="Show Calendar"></img>-->
                     </div>
             </td>
            </tr> 
          
          
          
                    
          <tr align="left" class="tdH">
            <td colspan="2">
              <strong>Proceeding Details</strong>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Proceeding No
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtPNo" id="txtPNo" maxlength="40"
                       size="38" readonly="readonly"/>  
               <!-- <select name="cmb_proc_no" id="cmb_proc_no" onchange="displaydetails()">
                <option value="0">--Select Proceeding No.--</option>
                <%
                
                   String pro_no="";
                   
                  ps=con.prepareStatement("select distinct PROCEEDING_NO from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and PROCESS_FLOW_STATUS_ID in ('CR','MD') and PROCEEDING_NO is not null");
                  ps.setInt(1,oid);
                  results=ps.executeQuery();
                   
                  while(results.next()) 
                  {
                    pro_no=results.getString("PROCEEDING_NO");                    
                  
                %>   
                   <option value="<%=pro_no%>"><%=pro_no%></option>  
                   <%
                   }
                   %>
                   </select>-->
                       
              </div>
            </td>
          </tr>
          </table>
          </td>
          </tr>
          
          
          
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="SUBMIT" name="butSub" id="butSub" value="Validate" />
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>