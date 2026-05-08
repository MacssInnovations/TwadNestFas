<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
  <title>VALIDATE&nbsp;CUT-OFF&nbsp;DATE CURRENT POSTING DETAIL</title>
   
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"       src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"     src="../scripts/CurrentPostingValidateJS.js">     </script>
  
    <link href="../../../../../css/Sample3.css" rel="stylesheet"          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
  </head>
  <body id="bodyid" > 


  <form name="frmCurrentPosting" id="frmCurrentPosting">
      <p>
        <%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet rs=null;
  
  
  try
  {
            ResourceBundle rsb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";
            String strDriver=rsb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rsb.getString("Config.DSN");
            String strhostname=rsb.getString("Config.HOST_NAME");
            String strportno=rsb.getString("Config.PORT_NUMBER");
            String strsid=rsb.getString("Config.SID");
            String strdbusername=rsb.getString("Config.USER_NAME");
            String strdbpassword=rsb.getString("Config.PASSWORD");
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
                  <th align="center" colspan="2">
                  <b>VALIDATE&nbsp;CUT-OFF&nbsp;DATE CURRENT POSTING DETAIL</b>
                  </th>
                </tr>
              
                <tr class="table">
                  <td>
                    <div align="left">Employee ID</div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtEmployeeid" id="txtEmployeeid"
                             maxlength="5" size="5"
                             onchange=" doFunction('loadempedit','null');"
                             onkeypress="return numbersonly1(event,this);"/>
                             <%
                             HttpSession session=request.getSession(false);
                             if(session.getAttribute("Admin")!=null && ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                             {
                             %>
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopup();">
                             <%}else{%>
                             
                              <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopupSR();">
                             <%}%>
                             <!--<img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="servicepopup();">-->
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
                             style="background-color: #ffffff"
                             maxlength="60" size="40" disabled="disabled"/>
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
                
                  <tr class="table">
                  <td>
                    <div align="left">
                      Designation<font color="#e70000">*</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                    <select name="cmbsgroup" id="cmbsgroup"  onfocus="checkempid()" onchange="getDesignation()">
                <option value="0">Select Service Group</option>
                        <%
          rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                
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
          
          }    
                
        %>
               
               </select> 
                    <!---  designation -->
                      <select name="cmbDesignation" id="cmbDesignation"
                              onclick="if(checkempid()==true)return checkGroup();" >
                        <option value="0"></option>
                        
                      </select>
                    </div>
                  </td>
                </tr>
                
                  <tr>
                                                <td class="table" align="left">
                                                      Grade
                                                      
                                                </td>
                                                <td class="table" align="left">
                                                 <input type="radio"
                                                             checked="CHECKED"
                                                             value="Normal"
                                                             name="Office_Grade" ></input>
                                                      Normal&nbsp;
                                                      <input type="radio"
                                                             value="Selection"
                                                             name="Office_Grade" ></input>
                                                      Selection&nbsp;
                                                      <input type="radio"
                                                             value="Special"
                                                             name="Office_Grade" ></input>
                                                      Special
                                                       <input type="radio"
                                                             value="Super Grade"
                                                             name="Office_Grade" ></input>
                                                      Super Grade
                                                     
                                                </td>
                                          </tr>
                <tr>
                 <td class="table">
                    <div align="left">
                      Current Status of the Employee<font color="#e70000">
                        *
                      </font>
                    </div>
                  </td>
                
                <td class="table">
                    <div align="left">
                     <select id="cmbstatus" name="cmbstatus" onchange="changeStatus()" onfocus="return checkempid()">
                     <option value="">Select the Employee Status</option>
                      <%
          rs=null;
           try
           {
           ps=connection.prepareStatement("select CUR_POST_REASON_ID,CUR_POST_REASON_DESC from HRM_MST_CUR_POST_STATUS  order by REASON_SEQ_NO");
            rs=ps.executeQuery();
             String strcode="";
            String strname="";          
            while(rs.next())
            {
                strcode=rs.getString("CUR_POST_REASON_ID");
                strname=rs.getString("CUR_POST_REASON_DESC");
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in status1.."+e);
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
        </table>
               
               <div id="divwork" style="display:none">
                <!-- offic structure starting -->
                <table cellspacing="2" cellpadding="3" border="1" width="100%">
                 <tr class="table">
                  <td colspan="2" class="tdH">Working</td>
                </tr>
                <tr class="table">
                  <td colspan="2">
                    <div align="left">
                      <table cellspacing="2" cellpadding="3" border="1"   width="100%">
                        <tr class="table">
                          <td>
                            <div align="left">Department Id 
                        </div>
                          </td>
                          <td>
                          <div align="left">
                          <input type="text" name="txtDept_Id_work"  id="txtDept_Id_work" maxlength="6" value="TWAD" readonly style=" background-color: #ececec"></input>&nbsp;
                         
                        </div>
                            
                          </td>
                        </tr>
                        <tr class="table">
                          <td>
                            <div align="left">
                              Office Id 
                              <font color="#e70000">*</font>
                            </div>
                          </td>
                          <td>
                          <div align="left"><!--onkeypress="return numbersonly1(event,this);"-->
                          <input type="text" name="txtOffice_Id"                                          
                                         id="txtOffice_Id" 
                                         onchange="return doFunction('Load',true)"
                                         onfocus="checkempid()"
                                         onkeypress="return numbersonly1(event,this);"
                                         maxlength="4"></input>&nbsp;<img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="Office_List" onclick="if(checkempid()==true){jobpopup();}">
                        </div>
                            
                          </td>
                        </tr>
                       
                        <tr class="table">
                          <td>
                            <font color="#808080">Name of the Office</font>
                          </td>
                          <td>
                            <input type="text" name="txtOffice_Name"
                                   id="txtOffice_Name"
                                   style="background-color: #FFFFFF"
                                   disabled="disabled" size="30"></input>
                          </td>
                        </tr>
                        <tr class="table">
                          <td>
                            <font color="#808080">Office Address</font>
                            <label style="color:rgb(255,0,0);"/>
                          </td>
                          <td>
                            <textarea name="txtOffice_Address1"
                                   id="txtOffice_Address1"
                                   style="background-color: #FFFFFF"
                                   disabled="disabled" cols="40" rows="4" ></textarea>
                          </td>
                        </tr>
                <!--        <tr class="table">
                          <td>
                            <font color="#808080">Office Address2</font>
                          </td>
                          <td>
                            <input type="text" name="txtOffice_Address2"
                                   id="txtOffice_Address2"
                                   style="background-color: #FFFFFF"
                                   disabled="disabled" size="60"></input>
                          </td>
                        </tr>
                        <tr class="table">
                          <td>
                            <font color="#808080">City/Town</font>
                            <label style="color:rgb(255,0,0);"/>
                          </td>
                          <td>
                            <input type="text" name="txtOffice_City"
                                   id="txtOffice_City"
                                   style="background-color: #FFFFFF"
                                   disabled="disabled" size="30"></input>
                            <select name="cmbDistrict" id="cmbDistrict"
                                    style="visibility:hidden" ></select>
                          </td>
                        </tr>-->
                            <tr class="table">
                  <td>
                    <div align="left">
                      Date From<font color="#e70000">&nbsp;</font>(dd/mm/yyyy) 
                      <font color="#e70000">*</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtWorkDateFrom" id="txtWorkDateFrom"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.frmCurrentPosting.txtWorkDateFrom);"
                           alt="Show Calendar"></img>
                       
                      <input type="radio" value="FN" checked="checked"
                             name="optWorkDateFrom" id="optWorkDateFrom1"
                             onclick="return checkempid();"></input>
                       FN&nbsp; 
                      <input type="radio" value="AN" name="optWorkDateFrom"
                             id="optWorkDateFrom2" onclick="return checkempid();"></input>
                       AN&nbsp;
                    </div>
                  </td>
                </tr>
                <tr class="table" id="wing" style="display:none">
                          <td>
                            Wing
                          </td>
                          <td>
                            <select name="cmbWing">
                            <option value="0">Select Wing</option>
                            </select>
                          </td>
                 </tr>
                 <tr class="table">
                          <td>
                            Remarks
                          </td>
                          <td>
                            <textarea name="txtworkremark" title="Don't type '&' Character while entering the remarks" rows=2 cols="50" ></textarea>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </td>
                </tr>
                </table>
                <!-- offic structure ending -->
              </div>
              
              
              
              
               
               <div id="divstudy" style="display:none">
                <table cellspacing="3" cellpadding="2" border="1" width="100%">
                  <tr class="tdTitle">
                    <td colspan="2">
                      <div align="left">
                        <strong>PG Studies</strong>
                      </div>
                    </td>
                  </tr>
                  <tr class="table">
                    <td>
                      <div align="left">
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
                      <div align="left">Institution Loaction<font color="#ff2121">*</font></div>
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
                        Course Name <font color="#ff2121">*</font>
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
                  <td  align="left">
                    <div align="left">
                      Date From<font color="#e70000">&nbsp;</font>(dd/mm/yyyy) 
                      <font color="#e70000">*</font>
                    </div>
                  </td>
                  <td  align="left">
                    <div align="left">
                      <input type="text" name="txtStudyDateFrom" id="txtStudyDateFrom"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.frmCurrentPosting.txtStudyDateFrom);"
                           alt="Show Calendar"></img>
                       
                      <input type="radio" value="FN" checked="checked"
                             name="optStudyDateFrom" id="optStudyDateFrom1"
                             onclick="return checkempid();"></input>
                       FN&nbsp; 
                      <input type="radio" value="AN" name="optStudyDateFrom"
                             id="optStudyDateFrom2" onclick="return checkempid();"></input>
                       AN&nbsp;
                    </div>
                  </td>
                </tr>
                 <tr class="table">
                          <td  align="left">
                            Remarks
                          </td>
                          <td  align="left">
                            <textarea name="txtstudyremark" rows=2 cols="50" ></textarea>
                          </td>
                </tr>
                 
                 
                </table>
              </div>
            
              
              
              
              
               <div id="divdep" style="display:none">
                <!-- offic structure starting -->
                <table cellspacing="2" cellpadding="3" border="1" width="100%">
                 <tr class="table">
                  <td colspan="2" class="tdH">Deputation</td>
                </tr>
                <tr class="table">
                  <td colspan="2">
                    <div align="left">
                      <table cellspacing="2" cellpadding="3" border="1"   width="100%">
                        <tr class="table">
                          <td>
                            <div align="left">Department Id 
                        </div>
                          </td>
                          <td>
                          <div align="left">
                         <!-- <input type="text" name="txtDept_Id"  id="txtDept_Id" maxlength="6" disabled="disabled"></input>&nbsp;-->
                         <select name="txtDept_Id"  id="txtDept_Id" onchange="checkdeptsel()" onfocus="return checkempid();">
                         <!--<option value="">Select the Department</option>-->
                         <option value="">Select the Other Department</option>
                         <%
                         try
                         {
                        ps=connection.prepareStatement("select a.OTHER_DEPT_ID,a.OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS a  order by OTHER_DEPT_NAME");
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
                        </div>
                            
                          </td>
                        </tr>
                        <tr class="table">
                          <td>
                            <div align="left">
                              Office Id 
                              <font color="#e70000">*</font>
                            </div>
                          </td>
                          <td>
                          <div align="left"><!--onkeypress="return numbersonly1(event,this);"-->
                          <input type="text" name="txtOthOffice_Id"
                                         id="txtOthOffice_Id" 
                                         onblur="return doFunction('Load',false)"
                                         onfocus="if(checkempid()==true)return checkdeptid();"
                                         onkeypress="return numbersonly1(event,this);"
                                         maxlength="4"></input>&nbsp;<img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="Other_Office_List" onclick="if(checkempid()==true){jobpopup1();}">
                        </div>
                            
                          </td>
                        </tr>
                       
                        <tr class="table">
                          <td>
                            <font color="#808080">Name of the Office</font>
                          </td>
                          <td>
                            <input type="text" name="txtOthOffice_Name"
                                   id="txtOthOffice_Name"
                                   style="background-color: #FFFFFF"
                                   disabled="disabled" size="30"></input>
                          </td>
                        </tr>
                        <tr class="table">
                          <td>
                            <font color="#808080">Office Address</font>
                            <label style="color:rgb(255,0,0);"/>
                          </td>
                          <td>
                            <textarea  name="txtOthOffice_Address1" cols="40" rows="4"
                                   id="txtOthOffice_Address1"
                                   style="background-color: #FFFFFF"
                                   disabled="disabled" ></textarea>
                          </td>
                        </tr>
                        <tr class="table">
                          <td>
                            <font color="#808080">Office Address2</font>
                          </td>
                          <td>
                            <input type="text" name="txtOthOffice_Address2"
                                   id="txtOthOffice_Address2"
                                   style="background-color: #FFFFFF"
                                   disabled="disabled" size="60"></input>
                          </td>
                        </tr>
                        <tr class="table">
                          <td>
                            <font color="#808080">City/Town</font>
                            <label style="color:rgb(255,0,0);"/>
                          </td>
                          <td>
                            <input type="text" name="txtOthOffice_City"
                                   id="txtOthOffice_City"
                                   style="background-color: #FFFFFF"
                                   disabled="disabled" size="30"></input>
                            <select name="cmbDistrict" id="cmbDistrict"
                                    style="visibility:hidden" ></select>
                          </td>
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
                      <input type="text" name="txtDepDateFrom" id="txtDepDateFrom"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.frmCurrentPosting.txtDepDateFrom);"
                           alt="Show Calendar"></img>
                       
                      <input type="radio" value="FN" checked="checked"
                             name="optDepDateFrom" id="optDepDateFrom1"
                             onclick="return checkempid();"></input>
                       FN&nbsp; 
                      <input type="radio" value="AN" name="optDepDateFrom"
                             id="optDepDateFrom2" onclick="return checkempid();"></input>
                       AN&nbsp;
                    </div>
                  </td>
                </tr>
                      <tr class="table">
                          <td>
                            Remarks
                          </td>
                          <td>
                            <textarea name="txtDepremark" rows=2 cols="50" ></textarea>
                          </td>
                        </tr>   
                      </table>
                    </div>
                  </td>
                </tr>
                </table>
                <!-- offic structure ending -->
              </div>
                
                
                
                <div id="divll" style="display:none">
                <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td colspan="2" class="tdH">Long Leave</td>
                </tr>
                 <tr class="table">
                  <td colspan="2">
                    <div align="left">
                      <table cellspacing="2" cellpadding="3" border="1"   width="100%">
                        <tr class="table">
                          <td>
                            <div align="left">Leave Type
                        </div>
                          </td>
                          <td>
                          <div align="left">
                         <select name="cmbleavetype"  id="cmbleavetype" >
                        <option value="">Select the Leave Type</option>
                        <%
          rs=null;
           try
           {
           ps=connection.prepareStatement("select LEAVE_TYPE_CODE,LEAVE_TYPE_DESC from HRM_MST_LEAVE_TYPES  order by LEAVE_TYPE_DESC");
            rs=ps.executeQuery();
             String strcode="";
            String strname="";          
            while(rs.next())
            {
                strcode=rs.getString("LEAVE_TYPE_CODE");
                strname=rs.getString("LEAVE_TYPE_DESC");
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in status2.."+e);
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
                      Date From<font color="#e70000">&nbsp;</font>(dd/mm/yyyy) 
                      <font color="#e70000">*</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtDateFrom" id="txtDateFrom"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.frmCurrentPosting.txtDateFrom);"
                           alt="Show Calendar"></img>
                       
                      <input type="radio" value="FN" checked="checked"
                             name="optDateFrom" id="optDateFrom1"
                             onclick="return checkempid();"></input>
                       FN&nbsp; 
                      <input type="radio" value="AN" name="optDateFrom"
                             id="optDateFrom2" onclick="return checkempid();"></input>
                       AN&nbsp;
                    </div>
                  </td>
                </tr>
               <!-- <tr class="table">
                  <td>
                    <div align="left">
                      Date To(dd/mm/yyyy)<font color="#ff0000">&nbsp;*</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtDateTo" id="txtDateTo"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="toimg" src="../../../../../images/calendr3.gif"
                           onclick="if(checkempid()==true)showCalendarControl(document.frmCurrentPosting.txtDateTo);"
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
               -->
                       <tr class="table">
                          <td>
                            Remarks
                          </td>
                          <td>
                            <textarea name="txtleaveremark" rows=2 cols="50" ></textarea>
                          </td>
                        </tr>
                        
                        
                        </table>
                    </div>
                  </td>
                </tr>
                </table>
                </div>
                
                
                 <div id="divsus" style="display:none">
                 <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td colspan="2" class="tdH">Suspended</td>
                </tr>
                <tr class="table">
                  <td colspan="2">
                    <div align="left">
                      <table cellspacing="2" cellpadding="3" border="1"   width="100%">
                       
                <tr class="table">
                  <td>
                    <div align="left">
                      Date Suspended From
                                <font color="#ff0000">
                                  *
                                </font>
                              </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtSusFrom" id="txtSusFrom"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.frmCurrentPosting.txtSusFrom);"
                           alt="Show Calendar"></img>
                       
                      <input type="radio" value="FN" checked="checked"
                             name="optSusFrom" id="optSusFrom1"
                             onclick="return checkempid();"></input>
                       FN&nbsp; 
                      <input type="radio" value="AN" name="optSusFrom"
                             id="optSusFrom2" onclick="return checkempid();"></input>
                       AN&nbsp;
                    </div>
                  </td>
                </tr>
                <tr class="table">
                          <td>
                            Reason for Suspension
                              <font color="#ff0000">
                                *
                              </font>
                            </td>
                          <td>
                            <textarea name="txtsusreson" rows=2 cols="50" ></textarea>
                          </td>
                        </tr>
                    <!--   <tr class="table">
                          <td>
                            Remarks
                          </td>
                          <td>
                            <textarea name="txtleaveremark" rows=2 cols="50" ></textarea>
                          </td>
                        </tr>-->
                        </table>
                    </div>
                  </td>
                </tr>
                </table>
                </div>
                
                
                 <div id="divabs" style="display:none">
                 <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td colspan="2" class="tdH">Absconded</td>
                </tr>
                <tr class="table">
                  <td colspan="2">
                    <div align="left">
                      <table cellspacing="2" cellpadding="3" border="1"   width="100%">
                       
                <tr class="table">
                  <td>
                    <div align="left">
                      Date from which Absconded<font color="#ff0000">
                                  *
                                </font>
                              </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtAbsFrom" id="txtAbsFrom"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.frmCurrentPosting.txtAbsFrom);"
                           alt="Show Calendar"></img>
                   
                       
                      <input type="radio" value="FN" checked="checked"
                             name="optAbsFrom" id="optAbsFrom1"
                             onclick="return checkempid();"></input>
                       FN&nbsp; 
                      <input type="radio" value="AN" name="optAbsFrom"
                             id="optAbsFrom2" onclick="return checkempid();"></input>
                       AN&nbsp;
                    </div>
                  </td>
                </tr>
                      <tr class="table">
                          <td>
                            Remarks
                          </td>
                          <td>
                            <textarea name="txtabsremark" rows=2 cols="50" ></textarea>
                          </td>
                        </tr>
                        </table>
                    </div>
                    
               
                  
                    
                    
                  </td>
                </tr>
                       </table>
                    </div>
                 
                   
               <!--  Transit  -->
                      <div id="divtrt" style="display:none">
                 <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td colspan="2" class="tdH">Transit</td>
                </tr>
                <tr class="table">
                  <td colspan="2">
                    <div align="left">
                      <table cellspacing="2" cellpadding="3" border="1"   width="100%">
                       
                <tr class="table">
                  <td>
                    <div align="left">
                       Transit Date From
                                <font color="#ff0000">
                                  *
                                </font>
                              </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtTrtFrom" id="txtTrtFrom"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.frmCurrentPosting.txtTrtFrom);"
                           alt="Show Calendar"></img>
                       
                      <input type="radio" value="FN" checked="checked"
                             name="optTrtFrom" id="optTrtFrom1"
                             onclick="return checkempid();"></input>
                       FN&nbsp; 
                      <input type="radio" value="AN" name="optTrtFrom"
                             id="optTrtFrom2" onclick="return checkempid();"></input>
                       AN&nbsp;
                    </div>
                  </td>
                </tr>
                <tr class="table">
                          <td>
                            Reason for Transit
                              <font color="#ff0000">
                                *
                              </font>
                            </td>
                          <td>
                            <textarea name="txtTrtreson" rows=2 cols="50" ></textarea>
                          </td>
                        </tr>
                  
                        </table>
                    </div>
                  </td>
                </tr>
                </table>
                </div>
                
                
               <!-- unautherized leave  --> 
                
                        <div id="divual" style="display:none">
                 <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td colspan="2" class="tdH">Unautherized Leave</td>
                </tr>
                <tr class="table">
                  <td colspan="2">
                    <div align="left">
                      <table cellspacing="2" cellpadding="3" border="1"   width="100%">
                       
                <tr class="table">
                  <td>
                    <div align="left">
                       Unautherized Leave Date From
                                <font color="#ff0000">
                                  *
                                </font>
                              </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtUalFrom" id="txtUalFrom"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.frmCurrentPosting.txtUalFrom);"
                           alt="Show Calendar"></img>
                       
                      <input type="radio" value="FN" checked="checked"
                             name="optUalFrom" id="optUalFrom1"
                             onclick="return checkempid();"></input>
                       FN&nbsp; 
                      <input type="radio" value="AN" name="optUalFrom"
                             id="optUalFrom2" onclick="return checkempid();"></input>
                       AN&nbsp;
                    </div>
                  </td>
                </tr>
                <tr class="table">
                          <td>
                            Reason for Unautherized Leave
                              <font color="#ff0000">
                                *
                              </font>
                            </td>
                          <td>
                            <textarea name="txtUalreson" rows=2 cols="50" ></textarea>
                          </td>
                        </tr>
                  
                        </table>
                    </div>
                  </td>
                </tr>
                </table>
                </div>
                
                
                
                
              <!--  Compulsary Wait  -->  
                
                        <div id="divcmw" style="display:none">
                 <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td colspan="2" class="tdH">Compulsary Wait</td>
                </tr>
                <tr class="table">
                  <td colspan="2">
                    <div align="left">
                      <table cellspacing="2" cellpadding="3" border="1"   width="100%">
                       
                <tr class="table">
                  <td>
                    <div align="left">
                       Compulsary Leave Date From
                                <font color="#ff0000">
                                  *
                                </font>
                              </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtCmwFrom" id="txtCmwFrom"
                             maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdt(this);"
                             onkeypress="return calins(event,this);"/>
                       
                      <img id="fromimg" src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.frmCurrentPosting.txtCmwFrom);"
                           alt="Show Calendar"></img>
                       
                      <input type="radio" value="FN" checked="checked"
                             name="optCmwFrom" id="optCmwFrom1"
                             onclick="return checkempid();"></input>
                       FN&nbsp; 
                      <input type="radio" value="AN" name="optCmwFrom"
                             id="optCmwFrom2" onclick="return checkempid();"></input>
                       AN&nbsp;
                    </div>
                  </td>
                </tr>
                <tr class="table">
                          <td>
                            Reason for Compulsary Wait
                              <font color="#ff0000">
                                *
                              </font>
                            </td>
                          <td>
                            <textarea name="txtCmwreson" rows=2 cols="50" ></textarea>
                          </td>
                        </tr>
                  
                        </table>
                    </div>
                  </td>
                </tr>
                </table>
                </div>
                
                    
                    
                 
                  </td>
                </tr>
                </table>
                </div>
                
                
                <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td colspan="2" class="tdH">
                    <div align="center">
                    <table border="0">
                    <tr>
                        <td>
                      <input type="button" name="cmdedit" value="Validate" id="cmdedit"
                             onclick="doFunction('Validate','null')" />
                       </td><td>
                      <input type="button" name="cmdclear" value="CLEAR ALL"
                             id="cmdclear"
                             onclick="doFunction('Clear','null')"/>
                       </td> 
                     <td>
                      <input type="button" name="cmdexit" value="Exit"
                             id="cmdexit"
                             onclick="self.close()"/>
                       </td> 
                        </tr>
                    </table>
                    </div>
                  </td>
                </tr>
              </table>
            
          
          
    </form></body>
   


</html>
