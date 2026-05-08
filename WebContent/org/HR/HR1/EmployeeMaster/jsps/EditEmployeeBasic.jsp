<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <script type="text/javascript" src="../scripts/EmpId_Script.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <title>Update Employee's Basic Details </title>
    <script type="text/javascript">
         
    function toCheckEId()
    {
           if((frmEmployee.txtEmpId.value=="") || (frmEmployee.txtEmpId.value.length<=0))
    {
        //alert(document.frmEmployee.Employee_Id.value);
         alert("Please Enter Employee_Id");
         frmEmployee.txtEmpId.focus();
         return false;
    }
            if(isNaN(frmEmployee.txtEmpId.value))
         {
         alert("Please Enter Numeric Employee_Id");
         frmEmployee.txtEmpId.value="";
         frmEmployee.txtEmpId.focus();
         return false;
         }
        return true;
    }
     function toLoad()
     {
        document.frmEmployee.txtEmpId1.focus();
     }
   
          </script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
  </head >
  <body id="bodyid" class="table" onload="toLoad()" style="cursor:default"><form name="frmEmployee" method="get"
                                              class="table">
      <table width="100%">
        <tr>
          <td align="center" colspan="2">
            <table border="1px" width="650px%">
              <tr>
                <td align="center" class="tdH" colspan="4">
                  <div align="left">
                    <center>
                      <b>Update Employee's Basic Details</b>
                    </center>
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <div align="left">
                    Temporary Employee Id:
                  </div></td>
                <td colspan="2">
                  <div align="left">
                    <input tabindex="1" type="text" name="txtEmpId1" maxlength=5
                           id="txtEmpId1" onchange="callServer('Existg','null')"
                           onkeypress="return  numbersonly1(event,this)"/><input tabindex="1"
                                                                                 type="HIDDEN"
                                                                                 name="EmpId"
                                                                                 id="EmpId"/><img src="../../../../../images/c-lovi.gif"
                                                                                                  width="20"
                                                                                                  height="20"
                                                                                                  alt="empList"
                                                                                                  onclick="servicepopup();"/>
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
                    <select name="Employee_Prefix" tabindex="2"
                            onfocus="return toFocus()">
                      <option value="Mr">
                        Mr
                      </option>
                      <option value="Mrs">
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
                    <input tabindex="2" size="10" maxlength="8" name="Employee_Initial"
                           onchange="return toCheck1()"
                           onfocus="return toFocus()"  onkeypress="return nonanum(event)" style="text-transform:uppercase"/>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input tabindex="2"  size="30" maxlength="40" name="Employee_Name"
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
                    <input type="text" name="Gpf_Number" maxlength="5"
                           size="10"
                           onkeypress="return  numbersonly1(event,this)"
                           onfocus="return toFocus()"/>
                  </div>
                </td>
              </tr>
              <tr>
        <td colspan="2" align="left">
            Is Consolidated Staff
        </td>
        <td>
            <input type="radio"  checked="CHECKED"
                            value="Y"
                            name="Consolidate"></input>
                            Yes
            <input type="radio"
                            value="N"
                            name="Consolidate"></input>
                            No
        </td>
   <tr>
   
              <tr>
                <td colspan="4" class="tdH" align="right">
                  <input type="Button" value="Update" name="cmdSub"
                         onclick="callServer('Update','null')"></input>
                  &nbsp;
                  <input type="Button" value=" Cancel " name="cmdCancel"
                         onclick="Exit()"></input>
                  <input type="button" id="cmdclear" name="clear" value="Clear"
                         onclick="clearAll()"></input>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form></body>
</html>