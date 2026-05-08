<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <script type="text/javascript" src="../scripts/ValidateEmployeeDetails.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <title>Employee Details Validation</title>
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
                      <b>Employee Details Validation</b>
                    </center>
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <div align="left">
                    Employee Id:
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
                <td  colspan="2">
                  <div align="left">
                    EmployeeName
                  </div>
                </td>
                <td colspan="2">
                  <div align="left">
                    <input tabindex="2" style="TEXT-TRANSFORM:UPPERCASE" size="30" maxlength="40" name="Employee_Name"
                          readonly />
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
                           readonly/>
                  </div>
                </td>
              </tr>
              
              
               <tr>
                <td class="tdH">
                  <div align="left">
                    &nbsp;
                  </div>
                </td>
                <td class="tdH" >
                  <div align="left">
                    Particulars
                  </div>
                </td>
                 <td class="tdH">
                  <div align="center" >
                    Available
                  </div>
                </td>
                <td class="tdH">
                  <div align="center" >
                    Validated
                  </div>
                </td>
              </tr>
              
               <tr>
                <td >
                  <div align="left">
                    a)
                  </div>
                </td>
                <td >
                  <div align="left">
                    Additional Details 
                  </div>
                </td>
                 <td >
                  <div align="center" id="divAddAvail">
                    &nbsp;
                  </div>
                </td>
                <td >
                  <div align="center" id="divAddValid">
                    &nbsp;
                  </div>
                </td>
              </tr>
              
              <tr>
                <td >
                  <div align="left">
                    b)
                  </div>
                </td>
                <td >
                  <div align="left">
                    SR Controlling Details
                  </div>
                </td>
                 <td >
                  <div align="center" id="divSRCAvail">
                    &nbsp;
                  </div>
                </td>
                <td >
                  <div align="center" id="divSRCValid">
                    &nbsp;
                  </div>
                </td>
              </tr>
              
              
               <tr>
                <td >
                  <div align="left">
                    c)
                  </div>
                </td>
                <td >
                  <div align="left">
                    Current Posting Details
                  </div>
                </td>
                 <td >
                  <div align="center" id="divCPAvail">
                    &nbsp;
                  </div>
                </td>
                <td >
                  <div align="center" id="divCPValid">
                    &nbsp;
                  </div>
                </td>
              </tr>
              
              <tr>
                <td >
                  <div align="left">
                    d)
                  </div>
                </td>
                <td >
                  <div align="left">
                    SR Details
                  </div>
                </td>
                 <td >
                  <div align="center" id="divSRDAvail">
                    &nbsp;
                  </div>
                </td>
                <td >
                  <div align="center" id="divSRDValid">
                    &nbsp;
                  </div>
                </td>
              </tr>
              
              <tr>
                <td colspan="4" class="tdH" align="right">
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