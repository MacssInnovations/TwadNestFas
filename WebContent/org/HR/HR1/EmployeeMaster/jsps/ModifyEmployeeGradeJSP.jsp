<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
  <title>EDIT EMPLOYEE GRADE DETAIL</title>
   
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"       src="../../../../../org/Library/scripts/checkDate.js"></script>
   <!-- <script type="text/javascript" src="../scripts/AjaxOfficeContactId.js"></script>-->
    <!-- <script type="text/javascript" src="../scripts/controllingOfficeContact.js"></script>-->
    <script type="text/javascript"     src="../scripts/ModifyEmployeeGradeJS.js">     </script>
  
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
                  <b>EDIT&nbsp;EMPLOYEE&nbsp;GRADE DETAIL</b>
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
                 
                  
               
                <tr>
                                                <td class="table" align="left">
                                                      Grade
                                                      
                                                </td>
                                                <td class="table" align="left">
                                                 <input type="radio"
                                                             
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
        </table>
               
               <div id="divwork" style="display:none">
                <!-- offic structure starting -->
                <!-- offic structure ending -->
              </div>
              
              
               <div id="divstudy" style="display:none"/>  
                
                        <div id="divcmw" style="display:none"/>
                
                    
                    
                    
                    
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
                      <input type="button" name="cmdedit" value="Update" id="cmdedit"
                             onclick="doFunction('Update','null')" />
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
