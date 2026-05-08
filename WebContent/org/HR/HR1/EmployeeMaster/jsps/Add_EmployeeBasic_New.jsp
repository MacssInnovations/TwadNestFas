<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<!--%@ page import="java.sql.*,java.util.*"%-->
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <script type="text/javascript" src="../scripts/Add_EmployeeBasic_New_JS.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <title>Add Employee's Basic Details with  Emp Id</title>
    <script type="text/javascript">
         
      function toLoad()
      {
        document.frmEmployee.txtEmpId1.focus();
      }
   
          </script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
  </head>
  <body id="bodyid" class="table" onload="toLoad()" style="cursor:default">
  <form name="frmEmployee" method="get" class="table">
      <table width="100%">
        <tr>
          <td align="center" colspan="2">
            <table border="1px" width="650px%">
              <tr>
                <td align="center" class="tdH" colspan="4">
                  <div align="left">
                    <center>
                      <b>Add Employee Details </b>
                    </center><p align="center">
                      ( Emp.Id Manually Assigned )
                    </p>
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <div align="left">
                    Employee Id
                  </div></td>
                <td colspan="2">
                  <div align="left">
                    <input tabindex="1" type="text" name="txtEmpId1" maxlength=5
                           id="txtEmpId1" onchange="onClear();callServer('Existg','null')"
                           onkeypress="return  numbersonly1(event,this)"/>
                    
                    <input tabindex="1" type="HIDDEN" name="EmpId" id="EmpId"/>
                  </div>
                </td>
              </tr>
              <tr>
                <td rowspan="2">
                  <div align="left">
                    EmployeeName<label style="color:rgb(255,0,0);"
                                       onfocus="return toFocus()">
                      *
                    </label>
                  </div>
                </td>
                <td>
                  <div align="left">
                    Prefix
                  </div></td>
                <td>
                  <div align="left">
                    Initial
                  </div></td>
                <td>
                  <div align="left">
                    Name<label style="color:rgb(255,0,0);">
                      *
                    </label>
                  </div>
                </td>
              </tr>
              <tr>
                <td>
                  <div align="left">
                    <select name="Employee_Prefix" tabindex="2" id="Employee_Prefix"
                            onfocus="return toFocus()">
                      <option value="Mr">
                        Mr
                      </option>
                      <option value="Ms">
                        Mrs
                      </option>
                      <option value="Thiru">
                        Thiru
                      </option>
                      <option value="Selvi">
                        Selvi
                      </option>
                    </select>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input tabindex="2" size="10" maxlength="8" name="Employee_Initial" id="Employee_Initial"
                           onchange="return toCheck1()"
                           onfocus="return toFocus()"  onkeypress="return nonanum(event)" style="text-transform:uppercase"/>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input tabindex="2" size="30" maxlength="40" name="Employee_Name" id="Employee_Name" 
                           onchange="return toCheck()" onfocus="return toFocus()"  onkeypress="return nonanum(event)"/>
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <div align="left">
                    GPF Number
                  </div>
                </td>
                <td colspan="2">
                  <div align="left">
                    <input type="text" name="Gpf_Number" maxlength="5" id="Gpf_Number"
                           size="10" onkeypress="return  numbersonly1(event,this)"  onblur="return notEqual()"  />
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="4" class="tdH" align="center">
                  <input type="Button" value="Add" name="cmdSub" id="cmdSub"
                         onclick="callServer('Verify','null')"></input>
                  &nbsp;
                  
                  <input type="button" id="cmdclear" name="clear" value="Clear"
                         onclick="clearAll()"></input>
                  <input type="Button" value=" Exit " name="cmdCancel"
                         onclick="Exit()"></input>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form></body>
</html>