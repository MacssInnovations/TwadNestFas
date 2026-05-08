<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Update Employee SR Controlling Office</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/UpdateSRControllingOffice.js"></script>
     <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <!--  <script type="text/javascript"       src="../../../../Library/scripts/CalendarControl.js"></script>-->
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
  
            <script type="text/javascript">
  
    function toLoad()
    {
      document.frmEmployee.txtEmpId1.focus();
    }
    </script>
  </head>
  <body onload="toLoad()">
  <form name="frmEmployee" method="GET">
                  <%
  
   Connection connection=null;
  PreparedStatement ps=null;
  Statement statement=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  int strNativeDistrict=0;
  int strNativeTaluk=0;
  String strEmpStatus="";
  
  
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
 
  
                                    <table border="1px" width="650px%" align="center">
                                          <tr>
                                                <td align="center" class="tdH"
                                                    colspan="4">
                                                      <center>
                                                            <b>Update Employee SR Controlling Office</b>
                                                      </center>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td colspan="2" class="table">Employee Id:<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                                <td colspan="2" class="table">
                                                      <input tabindex="1"
                                                             type="text"
                                                             name="txtEmpId1"
                                                             id="txtEmpId1"
                                                             maxlength=5
                                                             onkeypress="return  numbersonly1(event,this)"
                                                             onchange="callServer1('Load','null')"></input>
                                                      <input tabindex="1"
                                                             type="HIDDEN"
                                                             name="EmpId"
                                                             id="EmpId"></input>
                                                      <img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="empList"
                                                           onclick="servicepopup();"></img>
                                                </td>
                                          </tr>
                                          <tr>
                                                <td  class="table" colspan="2" >
                                                      EmployeeName
                                                     
                                                </td>
                                                <td class="table" colspan="2" > <input tabindex="2"
                                                             size="30" 
                                                             name="Employee_Name"  style="background-color: #ececec"
                                                            readonly  />
                                                      <input type="hidden"
                                                             name="Employee_Name1"/>
                                                </td>
                                          </tr>
                                         
                                                      
                                          <tr>
                                                <td colspan="2" class="table">
                                                      GPFNumber
                                                      
                                                </td>
                                                <td colspan="2" class="table">
                                                      <input type="text"
                                                             name="Gpf_Number"  style="background-color: #ececec"
                                                             maxlength="30"
                                                             size="15"
                                                            readonly/>
                                                      <input type="hidden"
                                                             name="Gpf_Number1"/>
                                                </td>
                                          </tr>
                                           <tr>
                                                <td colspan="4"
                                                    class="tdH">
                                                      <b><font face="Tahoma"
                                                               color="#000000"
                                                               size="2"> SR Controlling Office Details </font></b>
                                                </td>
                                          </tr>
                                          <!-- <tr>
                                                <td colspan="2" class="table">
                                                      Department Id
                                                    
                                                </td>
                                                <td colspan="2" class="table">
                                                      <input type="text"
                                                             name="Dept_Id" style="background-color: #ececec"
                                                             maxlength="5"
                                                             size="5" readonly value="TWAD"/>
                                                    
                                                </td>
                                          </tr>-->
                                         
                                           <tr>
                                                <td colspan="2" class="table">
                                                      Office&nbsp;Id<label style="color:rgb(255,0,0);">&nbsp;*</label>
                                                      
                                                </td>
                                                <td colspan="2" class="table">
                                                 <input type="hidden"    name="Dept_Id" >
                                                      <input type="text"
                                                             name="Office_Id"
                                                             maxlength="4" 
                                                             size="5"  
                                                            onkeypress="return  numbersonly1(event,this)" 
                                                            onchange="callServer1('ExistgOff','null');"
                                                            onfocus="return toFocus()"/>
                                                      <img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="OfficeList"
                                                           onclick="if(toFocus()!=false)jobpopup1();"/>
                                                </td>
                                          </tr>
                                         
                                          <tr>
                                                <td colspan="2" class="table">
                                                      Office Name
                                                    
                                                </td>
                                                <td colspan="2" class="table">
                                                      <input type="text"
                                                             name="Office_Name"
                                                             maxlength="30"
                                                             size="45" readonly />
                                                    
                                                </td>
                                          </tr>
                                          <tr>
                                              <td class="table" colspan="2">Office Address</td>
                                              <td class="table" colspan="2">
                                                <textarea name="txtOffAddress" cols="25" rows="5" id="txtOffAddress"  readonly></textarea>
                                              </td>
                                            </tr>
                                            <tr>
                                              <td class="table" colspan="2">Date effective From</td>
                                              <td class="table" colspan="2">
                                                <input type=text name="txtdtjoin" maxlength="10" onFocus="return toFocus(); javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                                <img src="../../../../../images/calendr3.gif" onclick="if(toFocus()==true)showCalendarControl(document.frmEmployee.txtdtjoin);" alt="Show Calendar"></img>
                                              </td>
                                            </tr>
                                            <!--<input type=hidden name="txtdtjoin" maxlength="10">-->
                                          <tr>
                                                <td colspan="4" class="tdH"
                                                    align="right">
                                                      <input type="button" name="cmbValidate" id="cmbValidate"
                                                             value="Update" onclick="callServer1('Update','null');"></input>
                                                      &nbsp;
                                                      <!--<input type="BUTTON" name="cmdDelete" value="    Delete    " id="cmdDelete" onclick="callServer1('Delete','null')"/>-->
                                                      <input type="Button"
                                                             value=" Cancel "
                                                             name="cmdCancel"
                                                             onclick="self.close();"></input>
                                                             &nbsp;
                                                      <input type="Button"
                                                             value=" ClearAll "
                                                             name="cmdClearAll"
                                                             onclick="clearAll();"></input>

                                                </td>
                                          </tr>
                                    </table>
                            
            </form>
                                          
                                          
  </body>
</html>