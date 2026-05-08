<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
  
    <!-- MICROSOFT/MOZILLA/NETSCAPE BROWSERS ALL REQUIRE THIS META TAG -->
    <meta http-equiv="Pragma" content="no-cache">
    
    <!-- MICROSOFT BROWSERS REQUIRE THIS ADDITIONAL META TAG AS WELL -->
    <meta http-equiv="Expires" content="-1">

    <meta name="archive" content="false">
    <meta http-equiv="imagetoolbar" content="no">
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> View Of Employee's Service Particulars</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"     src="../scripts/HRE_EmployeeServiceDetailsViewJS.js">     </script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
  </head>
  <body id="bodyid" > 


  <form name="HRE_EmployeeServiceDetails" id="HRE_EmployeeServiceDetails">
      <p>&nbsp;</p>
      <div align="center">
        <table width="100%">
          <tr>
            <td>
              <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <th align="center" colspan="2">
                  <b> View Of Employee's Service Particulars</b>
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
              </table>
              <table id="mytable" align="center" cellspacing="3" cellpadding="2"
                     border="1" width="100%">
                <tr class="tdH">
                  <th align="LEFT" colspan="12">Existing Service Details</th>
                </tr>
                <tr class="tdH">
                  <th>Status</th>
                  <th>Service&nbsp;No </th>
                  <th>Date&nbsp;From&nbsp;</th>
                  <th>Date&nbsp;From Session</th>
                  <th>Date&nbsp;To&nbsp;&nbsp;&nbsp;</th>
                  <th>Date&nbsp;To Session</th>
                  <th>Designation</th>
                  <th>Office&nbsp;Dept </th>
                  <th>Office&nbsp;Name </th>
                  <th>Emp&nbsp;Status&nbsp;Desc </th>
                <!--  <th>Status&nbsp;Detail </th>-->
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
