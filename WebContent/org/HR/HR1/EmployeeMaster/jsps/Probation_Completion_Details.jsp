<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Probation Completion Details</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/probation_Completion_DetailsJS.js"></script>
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
  <form name="frmEmployee">
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
             System.out.println("connected");
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
                                                            <b>Probation Completion Details</b>
                                                      </center>
                                                </td>
                                          </tr>
                                          
                                          <tr>
                                                <td colspan="4"
                                                    class="tdH">
                                                      <b><font face="Tahoma"
                                                               color="#000000"
                                                               size="2"> Employee Details </font></b>
                                                </td>
                                          </tr>
                                          
                                          <tr>
                                                <td colspan="2" class="table">Employee Id<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                                <td colspan="2" class="table">
                                                      <input tabindex="1"
                                                             type="text"
                                                             name="txtEmpId1"
                                                             id="txtEmpId1"
                                                             maxlength=5
                                                             size="8"
                                                             onkeypress="return  numbersonly1(event,this)"
                                                             onchange="callServer1('Load','null')"
                                                              onblur="comboCadre()"></input>
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
                                                      Employee Name
                                                     
                                                </td>
                                                <td class="table" colspan="2" > <input tabindex="2"
                                                             size="45" 
                                                             name="Employee_Name"  style="background-color: #ececec"
                                                            readonly  />
                                                      <input type="hidden"
                                                             name="Employee_Name1"/>
                                                </td>
                                          </tr>
                                         
                                                      
                                          <tr>
                                                <td colspan="2" class="table">
                                                      GPF Number
                                                      
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
                                                <td colspan="2" class="table">
                                                      Current Designation
                                                      
                                                </td>
                                                <td colspan="2" class="table">
                                                      <input type="text"
                                                             name="txtCurr_desig" id="txtCurr_desig"  style="background-color: #ececec"
                                                             maxlength="40"
                                                             size="40"
                                                            readonly/>
                                                      
                                                </td>
                                          </tr>
                                          
                                           <tr>
                                                <td colspan="4"
                                                    class="tdH">
                                                      <b><font face="Tahoma"
                                                               color="#000000"
                                                               size="2"> Probation Details </font></b>
                                                </td>
                                          </tr>
                                         
                                         <tr>
                                                <td colspan="2" class="table">
                                                      Cadre Post Held
                                                      <label style="color:rgb(255,0,0);">&nbsp;*</label>
                                                </td>
                                                <td colspan="2" class="table">
                                                      <select  name="cmb_Cad" id="cmb_Cad" size="1" >
                                                      <option value="">---Select Cadre---</option>
                                                      </select>
                                                      
                                                </td>
                                          </tr>
                                         
                                         
                                          <tr>
                                              <td class="table" colspan="2">Date of Probation Completion<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                              <td class="table" colspan="2">
                                                <input type=text name="txtdtPC" maxlength="10" size="10" onFocus="return toFocus(); javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                                <img src="../../../../../images/calendr3.gif" onclick="if(toFocus()==true)showCalendarControl(document.frmEmployee.txtdtPC);" alt="Show Calendar"></img>
                                              </td>
                                            </tr>
                                            
                                            <tr>
                                                <td colspan="2" class="table">
                                                      Proceedings No.
                                                      
                                                </td>
                                                <td colspan="2" class="table">
                                                      <input type="text"
                                                             name="txtP_No" id="txtP_No"  style="background-color: #ececec"
                                                             maxlength="40"
                                                             size="40"/>
                                                      
                                                </td>
                                          </tr>
                                          
                                          <tr>
                                              <td class="table" colspan="2">Proceeding Date</td>
                                              <td class="table" colspan="2">
                                                <input type=text name="txtPdt" maxlength="10" size="10" onFocus="return toFocus(); javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="datefun()">
                                                <img src="../../../../../images/calendr3.gif" onclick="if(toFocus()==true)showCalendarControl(document.frmEmployee.txtPdt);" alt="Show Calendar"></img>
                                              </td>
                                            </tr>
                                          
                                          <tr>
                                                <td colspan="2" class="table">
                                                      Remarks
                                                      
                                                </td>
                                                <td colspan="2" class="table">
                                                      <!--<textarea  cols="30" rows="4" name="txt_rem" id="txt_rem">-->
                                                      <textarea cols="30" rows="4" name="txt_rem" id="txt_rem" onkeypress="return noEnter(event)" onblur="datefun()">
                                                      </textarea>
                                                </td>
                                          </tr>
                                          
                                          <tr>
                                                <td colspan="4" class="tdH"
                                                    align="center">
                                                     <input type="button" name="cmbAdd" id="cmbAdd"
                                                             value="Add" onclick="callServer1('Add','null');"></input>
                                                      &nbsp;
                                                      
                                                                                  
                                                      <input type="button" name="cmbUpdate" id="cmbUpdate"
                                                             value="Update" onclick="callServer1('Update','null');" disabled="disabled"></input>
                                                      &nbsp; 
                                                      
                                                      <input type="button" name="cmbDel" id="cmbDel"
                                                             value="Delete" onclick="callServer1('Delete','null');" disabled="disabled"></input>
                                                             &nbsp;
                                                      
                                                      <input type="Button"
                                                             value=" Clear "
                                                             name="cmdClearAll"
                                                             onclick="clearAll();"></input>
                                                             &nbsp; 
                                                             
                                                    <input type="Button"
                                                             value=" Exit "
                                                             name="cmdCancel"
                                                             onclick="self.close();"></input>
                                                             

                                                </td>
                                          </tr>
                                          
                   <tr>
                  <td colspan="4" height="56">
                  <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                  <tr class="table">
                        
                        <th>Select</th>
                        <th>Cadre Post Held</th>
                        <th>Date of Probation Completion</th>
                        <th>Proceeding No.</th>
                        <th>Proceeding Date</th>  
                        <th>Remarks</th>                
                        
                      </tr>    
                      <tbody id="grid_body" class="table" align="left" >
                       </tbody>
                  </table>
                  </td>
                  </tr>
                                          
                                          
                                    </table>
                            
            </form>
                                          
                                          
  </body>
</html>