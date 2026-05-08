<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <title>Employee Profile</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Employee_ProfileJS.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
   </head>
  <body id="bodyid"><form name="frmEmployeeProfile"
                          action="EmployeeProfileServ.rep" method="post">
      <p>&nbsp;</p>
      <div align="center">
        <table width="100%">
          <tr>
            <td>
              <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <th align="center" colspan="2">
                    <b>Employee Profile</b>
                  </th>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">Employee ID</div>
                  </td>
                  <td>
                    <div align="left">
                    <input tabindex="1" type="text" name="txtEmpId1" maxlength=5
                           id="txtEmpId1" onchange="callServer('Existg','null')"
                           onkeypress="return  numbersonly1(event,this)"/><input tabindex="1"
                                                                                 type="HIDDEN"
                                                                                 name="EmpId"
                                                                                 id="EmpId"/>
                      <img src="../../../../../../images/c-lovi.gif" width="20"
                           height="20" alt="empList"
                           onclick="servicepopup();"></img>
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
                       <td align="left">Report&nbsp;Type</td>
                       <td align="left"><select name="cmbReportType">
                              <option value="PDF" selected="selected">PDF</option>
                              <option value="EXCEL">EXCEL</option>
                            <!--  <option value="TXT">TXT</option>-->
                              <option value="HTML">HTML</option>
                            </select> 
                        </td>
                </tr>      
                <tr class="tdH">
                  <td colspan="2">
                    <div align="center">
                        <input type="hidden" id="photoPath" name="photoPath"></input>
                      <input type="button" id="cmdsubmit" name="Submit"
                             value="Submit" onclick="return btnsubmit()"></input>
                       
                      <input type="reset" value="Clear"></input>
                       
                      <input type="button" value="Exit" onclick="self.close()"></input>
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
