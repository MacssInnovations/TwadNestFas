<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>SR Intermediate Period Updation</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"       src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"     src="../scripts/HRE_EmployeeIntermediateUpdateJS.js">     </script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"         media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"         media="screen"/>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
  </head>
  <body id="bodyid" > 


  <form name="HRE_EmployeeServiceDetails" id="HRE_EmployeeServiceDetails">
      <p>
        <%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  
 
  
  try
  {
  
            ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs.getString("Config.DSN");
            String strhostname=rs.getString("Config.HOST_NAME");
            String strportno=rs.getString("Config.PORT_NUMBER");
            String strsid=rs.getString("Config.SID");
            String strdbusername=rs.getString("Config.USER_NAME");
            String strdbpassword=rs.getString("Config.PASSWORD");

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
      </p>
      <p>&nbsp;</p>
      <div align="center">
        <table width="100%">
          <tr>
            <td>
              <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <th align="center" colspan="2" >
                  <b>SR Intermediate Period Updation</b>
                  </th>
                </tr>
                <!-- OFFICE DETAILS -->
                <% 
        HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    //int empid=11263;
    int  oid=0;
    String oname="",oadd1="",oadd2="",ocity="",olid="",owid="";
    String olname=""; 
    String ownature="",deptid="",type="";
    try
    {
           
            ps=connection.prepareStatement("select OFFICE_ID,DEPARTMENT_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                    deptid=results.getString("DEPARTMENT_ID");
                 
                 }
            results.close();
            ps.close();
            System.out.println("office id:"+oid);
            System.out.println("dept id:"+deptid);
            if( deptid==null || deptid.equalsIgnoreCase("TWAD"))
            {
                    type="(TWAD)";
                    ps=connection.prepareStatement("select OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,OFFICE_LEVEL_ID,PRIMARY_WORK_ID from COM_MST_OFFICES where OFFICE_ID=?" );
                    ps.setInt(1,oid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oname=results.getString("OFFICE_NAME");
                            oadd1=results.getString("OFFICE_ADDRESS1");
                            if(oadd1==null)oadd1="";
                            oadd2=results.getString("OFFICE_ADDRESS2");
                            if(oadd1==null)oadd1="";
                            ocity=results.getString("CITY_TOWN_NAME");
                            olid=results.getString("OFFICE_LEVEL_ID");
                            owid=results.getString("PRIMARY_WORK_ID");
                          }
                          //System.out.println("office name:"+oname);
                    results.close();
                    ps.close();
                  
                    ps=connection.prepareStatement("select OFFICE_LEVEL_NAME from COM_MST_OFFICE_LEVELS where OFFICE_LEVEL_ID=?" );
                    ps.setString(1,olid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            olname=results.getString("OFFICE_LEVEL_NAME");
                            
                          }
                    results.close();
                    ps.close();
                    
                    ps=connection.prepareStatement("select WORK_NATURE_DESC from COM_MST_WORK_NATURE where WORK_NATURE_ID=?" );
                    ps.setString(1,owid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            ownature=results.getString("WORK_NATURE_DESC");
                            
                          }
                    results.close();
                    ps.close();
            }
            else
            {
                System.out.println("other");
                System.out.println("off id::"+oid);
                    type="(OTHER="+deptid+")";
                    String otherdeptid="";
                    ps=connection.prepareStatement("select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_NAME,ADDRESS1,ADDRESS2,CITY_TOWN from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ID=?" );
                    ps.setInt(1,oid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oname=results.getString("OTHER_DEPT_OFFICE_NAME");
                            System.out.println("oname::"+oname);
                            oadd1=results.getString("ADDRESS1");
                            if(oadd1==null)oadd1="";
                            oadd2=results.getString("ADDRESS2");
                            if(oadd2==null)oadd2="";
                            ocity=results.getString("CITY_TOWN");
                            otherdeptid=results.getString("OTHER_DEPT_ID");
                           // owid=results.getString("PRIMARY_WORK_ID");
                          }
                    results.close();
                    ps.close();
                  
                    ps=connection.prepareStatement("select OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?" );
                    ps.setString(1,otherdeptid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            olname=results.getString("OTHER_DEPT_NAME");
                            
                          }
                    results.close();
                    ps.close();
                    
                  
            }
            
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
                <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">Office ID</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <font color="#808080">
                        <input type="text" name="txtCOffice_ID"
                               id="txtCOffice_ID" value="<%=oid%>"
                               style="background-color: #FFFFFF"
                               disabled="disabled" readonly="readonly"></input>
                      </font>
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">Name of the Office</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      
                             <table >
                                <tr>
                                    <td align="left" rowspan="2"><input type="text" name="txtCOffice_Name"
                             id="txtCOffice_Name"  value="<%=oname%>"
                             style="background-color: #FFFFFF"
                             disabled="disabled" size="50"  readonly="readonly"></input>
                             </td>
                                    <td align="left" width="30%">
                            <font color="#000000">
                              <strong>Level</strong>
                            </font></td>
                                    <td align="left" width="30%">
                            <strong>Type</strong></td>
                                </tr>
                                <tr>
                                    <td align="left"><label ><%=olname%>
                                                        </label></td>
                                    <td align="left"><label ><%=ownature%><%=type%></label></td>
                                </table>
                                    
                                    
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td rowspan="2">
                    <div align="left">
                      <font color="#808080">Office Address</font>
                    </div>
                    <div align="left"/>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtCOffice_Address1"
                             id="txtCOffice_Address1"  value="<%=oadd1==null?"":oadd1%>"
                             style="background-color: #FFFFFF"  readonly="readonly"
                             disabled="disabled" size="60"></input>
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">
                      <input type="text" name="txtCOffice_Address2"
                             id="txtCOffice_Address2"  value="<%=oadd2==null?"":oadd2%>"
                             style="background-color: #FFFFFF"  readonly="readonly"
                             disabled="disabled" size="60"></input>
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">City/Town</font><label style="color:rgb(255,0,0);"/>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtCOffice_City"
                             id="txtCOffice_City"  value="<%=ocity==null?"":ocity%>"
                             style="background-color: #FFFFFF"  readonly="readonly"
                             disabled="disabled" size="30"></input>
                    </div>
                  </td>
                </tr>
                <!-- OFFICE DETAILS  -->
                <tr class="table">
                  <td>
                    <div align="left">Employee ID</div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtEmployeeid" id="txtEmployeeid"
                             maxlength="5" size="5"
                             onchange=" doFunction('loademp','null');"
                             onkeypress="return numbersonly1(event,this);"/>
                             <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopup();">
                    </div>
                  </td>
                </tr>
                
                <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">Employee Name</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtEmployee" id="txtEmployee"
                             style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff"
                             maxlength="60" size="40" disabled="disabled"/>
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">Date of Birth</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtdob" id="txtdob"
                             maxlength="10" size="10" disabled="disabled"
                             style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff"/>
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">GPF No.</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtGpf" id="txtGpf"
                             maxlength="10" size="10" disabled="disabled"
                             style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff"/>
                    </div>
                  </td>
                </tr>
                <tr class="tdH">
                  <th colspan="2">
                    <div align="left">Intermediate Period</div>
                  </th>
                </tr>
                
                <tr class="table">
                  <td>
                    <div align="left">
                      Date From<font color="#e70000">&nbsp;</font>(dd/mm/yyyy) 
                      <font color="#e70000">*</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="hidden" name="txtSNo" id="txtSNo"  maxlength="10" size="10">
                      <input type="hidden" name="txtDateFromOrg" id="txtDateFromOrg"  maxlength="10" size="10">
                      <input type="hidden" name="optDateFromOrg" id="optDateFromOrg"  maxlength="3" size="3">
                      <input type="text" name="txtDateFrom" id="txtDateFrom"  maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();" disabled="disabled"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                    <!--  <img id="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.HRE_EmployeeServiceDetails.txtDateFrom);"
                           alt="Show Calendar"></img> -->
                       
                      <input type="radio" value="FN" checked="checked"
                             name="optDateFrom" id="optDateFrom1"
                             onclick="return checkempid();" disabled></input>
                       FN&nbsp; 
                      <input type="radio" value="AN" name="optDateFrom"
                             id="optDateFrom2" onclick="return checkempid();" disabled></input>
                       AN&nbsp;
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">
                      Date To(dd/mm/yyyy)<font color="#ff0000">&nbsp;*</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="hidden" name="txtDateToOrg" id="txtDateToOrg"  maxlength="10" size="10">
                      <input type="hidden" name="optDateToOrg" id="optDateToOrg"  maxlength="3" size="3">
                      <input type="text" name="txtDateTo" id="txtDateTo"  maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="toimg" src="../../../../../images/calendr3.gif"
                           onclick="if(checkempid()==true)showCalendarControl(document.HRE_EmployeeServiceDetails.txtDateTo);"
                           alt="Show Calendar"></img>
                       
                      <input type="radio" value="FN" 
                             name="optDateTo" id="optDateTo1"
                             onclick="return checkempid();"></input>
                       FN&nbsp; 
                      <input type="radio" value="AN" name="optDateTo" checked="checked"
                             id="optDateTo2" onclick="return checkempid();"></input>
                       AN&nbsp;
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">
                      Employee Status&nbsp;during&nbsp;this&nbsp;period 
                      <font color="#e70000">*</font>
                    </div>
                  </td>
                  <td align="left">
                  <input type="hidden" name="optstatusOrg" id="optstatusOrg"  maxlength="10" size="10">
                    <input type="radio" name="optstatus" value="LLV">Leave</input>
                    <input type="radio" name="optstatus" value="TRT">Transit</input>
                    <input type="radio" name="optstatus" value="DPT">Deputation Transit</input>
                    <input type="radio" name="optstatus" value="TCL">Transit Cum Leave</input>
                  </td>
                </tr>
            <!--    <tr class="table">
                  <td>
                    <div align="left">Status Details</div>
                  </td>
                  <td>
                    <div align="left">
                    <input type="hidden" name="txtDetailOrg"   id="txtDetailOrg" >
                      <textarea cols="50" rows="5" name="txtDetail"   id="txtDetail" onfocus="trm(this);return checkempid();"></textarea>
                    </div>
                  </td>
                </tr>-->
                <tr class="table">
                  <td>
                    <div align="left">Remarks</div>
                  </td>
                  <td>
                    <div align="left">
                    
                     <input type="hidden" name="txtDetailOrg"   id="txtDetailOrg" >
                      <input type="hidden" name="txtDetail"   id="txtDetail">
                    
                     <input type="hidden" name="txtRemarkOrg" id="txtRemarkOrg"     maxlength="50" size="66" >
                    <!--  <input type="text" name="txtRemark" id="txtRemark"     maxlength="50" size="66" onfocus="trm(this);return checkempid();"/>-->
                      <textarea cols=50 rows=2 name="txtRemark" title="Don't type '&' Character while entering the remarks" id="txtRemark"   onfocus="trm(this);return checkempid();"></textarea>
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td colspan="2">
                    <div align="center">
                    <table border="0">
                    <tr>
                      <!--  <td>
                      <input type="button" name="cmdadd" value="Add" id="cmdadd"
                             onclick="doFunction('Add','null')"  style="display:block"/>
                       </td> -->
                       <td>
                      <input type="button" name="cmdupdate" value="UPDATE"
                             id="cmdupdate"
                             onclick="doFunction('Update','null')"
                             style="display:none"/>
                       </td> 
                       <!--<td>
                      <input type="button" name="cmddelete" value="DELETE"
                             id="cmddelete"
                             onclick="doFunction('Delete','null')"
                             disabled="disabled" />
                       </td>-->
                       <td>
                      <input type="button" name="cmdclear" value="CLEAR ALL"
                             id="cmdclear"
                             onclick="doFunction('Clear','null')"/>
                       </td> <td>
                     <!-- <input type="button" name="cmdlist" value="LIST ALL"
                             id="cmdlist" onclick="popwindow()"/>-->
                            </td>
                <td align="right">
                    <div align="right">
                Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbpagination" onchange="changepagesize()">
                  <option value="5" selected="selected">5</option>
                  <option value="10" selected>10</option>
                  <option value="15">15</option>
                  <option value="20">20</option>
                </select>
                </div>
                </td>
                        </tr>
                    </table>
                    </div>
                  </td>
                </tr>
              </table>
              <table id="mytable" align="center" cellspacing="3" cellpadding="2"
                     border="1" width="100%">
                <tr class="tdH">
                  <th align="LEFT" colspan="12">Existing Service Details</th>
                </tr>
                <tr class="tdH">
                  <th>Select</th>
                  <th>Service&nbsp;No </th>
                  <th>Date&nbsp;From&nbsp;</th>
                  <th>Date&nbsp;From Session</th>
                  <th>Date&nbsp;To&nbsp;&nbsp;&nbsp;</th>
                  <th>Date&nbsp;To Session</th>
                  <th>Emp&nbsp;Status&nbsp;Desc </th>
               <!--   <th>Status&nbsp;Detail </th>-->
                  <th>Remarks </th>
                </tr>
                <tbody id="tb" class="table">
                </tbody>
              </table>
              <table align="center" cellspacing="3" cellpadding="2" border="1"
                     width="100%">
                     <tr class="tdH">
                  <td>
                    <table align="center" cellspacing="3" cellpadding="2"
                           border="0" width="100%">
                      <tr>
                        <td width="30%">
                          <div align="left">
                            <div id="divpre" style="display:none"></div>
                          </div>
                        </td>
                        <td width="40%">
                          <div align="center">
                            <table border="0">
                              <tr>
                                <td>
                                  <div id="divcmbpage" style="display:none">
                                    Page&nbsp;&nbsp;<select name="cmbpage"
                                                            id="cmbpage"
                                                            onchange="changepage()"></select>
                                  </div>
                                </td>
                                <td>
                                  <div id="divpage"></div>
                                </td>
                              </tr>
                            </table>
                          </div>
                        </td>
                        <td width="30%">
                          <div align="right">
                            <div id="divnext" style="display:none"></div>
                          </div>
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr class="tdH">
                  <td>
                    <div align="center">
                    <input type="button" id="Submit" name="Submit" value="Submit"
                             onclick="doFunction('Submit','null')"></input>
                     <input type="button" id="Exit" name="Exit" value="Exit"
                             onclick="toExit()"></input>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>
