<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page  session="false"  import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<meta http-equiv="cache-control" content="no-cache">

<title>Request New Registration</title>

<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
   <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
   
<script language="javascript" type="text/javascript" src="../scripts/AjaxReqNewRegn.js"></script>
 <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
</head>
<!--onload="loadDate();loadYear();getSeqNo();"-->
<body >
<%
  Connection connection=null;
  Statement statement=null;
  ResultSet results=null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

    try
    {
      statement=connection.createStatement();
    }
    catch(SQLException e)
    {
    }
  }
  catch(Exception e)
  {
  }
  %><form name="frmNewRegn" method="POST" 
          action="../../../../../NewReqRegn_Servlet_EDIT.view" onsubmit="return nullCheck()" >
                   
                     <table cellspacing="1" cellpadding="2" width="92%"
                            align="center" border="1">
                            <tr>
                                   <td class="tdH">
                                          <div  align="center">
                                                 <strong><font size="4">CONTRACTOR'S REGISTRATION DETAILS</font></strong>
                                          </div>
                                   </td>
                            </tr>
                     </table>
                     <table cellspacing="2" cellpadding="3" border="1"
                            align="center" width="92%" class="table">
                            <!--<tr>
                            <td colspan="2" class="tdH">
                                   
                                     <P>
                                       <FONT size="3"><FONT size="4"><strong>REQUEST FOR NEW REGISTRATION OF CONTRACTORS</strong> </FONT></FONT>
</P>
                                     <P>&nbsp;</P>
                                          </div></td>
                     </tr>-->
                            
                            <tr>
                                   <td colspan="2" class="tdH" align="left">
                                    Office Details
                                   </td>
                            </tr>
                            <tr>
                                   <td>
                                          Office ID<font color="#cc3333">*</font>
                                  </td>
                                   <td>
                                          <input type="text" name="txtOffID"
                                                 maxlength="4" size="4"
                                                 onkeypress="return numbersonly2(event,this);"
                                                 onChange="getOfficeDetails();"/>
                                          <input type="HIDDEN" name="htxtOffID"
                                                 id="htxtOffID"/>
                                                 
                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="Office_List" onclick="jobpopup();">
                                         
                                   </td>
                            </tr>
                             <tr>
                                   <td>
                                          Office Name
                                   </td>
                                   <td>
                                         <input type="text" name="txtOffName"
                                                 readonly="readonly" size="40" maxlength="40"/>
                                   </td>
                            </tr>
                            <tr>
                                   <td>
                                          Office Address
                                   </td>
                                   <td>
                                          <textarea rows="4" cols="40" name="txtoffAddress" ></textarea>
                                         
                                   </td>
                            </tr>
                            <tr>
                                   <td colspan="2" class="tdH" align="left">
                                    Contractor Registration Details
                                   </td>
                            </tr>
                            <tr>
                                   <td>
                                          Registration Date (dd/mm/yyyy)
                                          <font color="#cc3333">*</font>
                                   </td>
                                   <td>
                                         <input type="text" name="txtDate" id="txtDate"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3';return chkOffice()  "
                       onkeypress="return calins(event,this);"
                       onchange="return regvalidupto()"
                       onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                 
            <!--    <img src="../../../../../images/calendr3.gif"
                     onclick="showCalendarControl(document.frmNewRegn.txtDate);"
                     alt="Show Calendar"></img>-->
                                   </td>
                            </tr>
                            <tr>
                                   <td>
                                          Registration Sl.No
                                          <font color="#cc3333">*</font>
                                   </td>
                                   <td>
                                          <input type="text" name="txtResNo"
                                          onfocus="return chRegDate()"
                                          onchange="return Load_Edit_verifyRegID()"     
                                          onkeypress="return numbersonly2(event,this);"
                                                 maxlength="5" size="5"/>
                                         
                                   </td>
                            </tr>
                             <tr>
                                   <td colspan="2" class="tdH" align="left">
                                    Contractor Details
                                   </td>
                            </tr>
                           <tr>
                                   <td>
                                           Contractor Id 
                                          <font color="#cc3333">*</font>
                                   </td>
                                   <td>
                                          <input type="text" readonly="readonly" class="disab" id="txtContID" size="6"
                                                 name="txtContID"/> ( System Generated )
                                   </td>
                            </tr>
                            <tr>
                                   <td>
                                          Name of the Contractor/Firm
                                          <font color="#cc3333">*</font>
                                   </td>
                                   <td>
                                          <input type="text"
                                                 name="txtContName"/>
                                   </td>
                            </tr>
                           
                            <tr>
                                   <td>
                                          Address
                                          <font color="#cc3333">*</font>
                                   </td>
                                   <td>
                                         
                                                 <textarea rows=4 cols=40
                                                        name="txtadd"></textarea>
                                         
                                   </td>
                            </tr>
                            <tr>
                                   <td>
                                          Phone
                                         
                                   </td>
                                   <td>
                                          <input type="text" name="txtPhone"
                                                 maxlength="15" size="15"/>
                                   </td>
                            </tr>
                           
                           
                           
                            <tr>
                                   <td>Email ID</td>
                                   <td>
                                          <input type="text" name="txtEmail" onchange="EmailCheck()";/>
                                   </td>
                            </tr>
                            
                            
                             <tr>
                                   <td colspan="2" class="tdH" align="left">
                                    Details of Registration
                                   </td>
                            </tr>
                            
                            
                             
                            <tr>
                                   <td>
                                          Class Of Registration
                                          <font color="#cc3333">*</font>
                                   </td>
                           <td>
                           
                            <input type="HIDDEN" name="htxtClass"
                                   id="htxtClass"/>
                          
                            
                                   <select size="1" name="txtClass"  >
                                          <option value="0">--Select Here--</option>
                  <%                        
                                          try
                {
                PreparedStatement ps=connection.prepareStatement("select REGN_CLASS_ID,REGN_CLASS_DESC from PMS_MST_CON_CLASS");
                ResultSet rs=ps.executeQuery();
                while(rs.next())
                {
                int pos=rs.getInt("REGN_CLASS_ID");
                out.println("<option value="+pos+">"+rs.getString("REGN_CLASS_DESC")+"</option>");
                }
                }
                catch(Exception e)
                {
                System.out.println("Exception in post rank combo..."+e);
                }
                %>
                                   </select>
                            </td>
                            </tr>
                            
                            <tr>
                                   <td>
                                          Statewise Coverage Requested?
                                          <font color="#cc3333">*</font>
                                   </td>
                                   <td>
                                          <input type="radio" name="State"
                                                 value="Y" checked="checked"/>
                                          Yes
                                          <input type="radio" name="State"
                                                 value="N"/>
                                          No
                                   </td>
                            </tr>
                            <tr>
                                   <td>
                                          Registration Reneval due on (dd/mm/yyyy)
                                          <font color="#cc3333">*</font>
                                   </td>
                                   <td>
                                        <input type="text" name="txtDate_Upto" id="txtDate_Upto"
                       maxlength="10" size="11"
                       readonly/>
                 
          <!--      <img src="../../../../../images/calendr3.gif"
                     onclick=" showCalendarControl(document.frmNewRegn.txtDate_Upto);"
                     alt="Show Calendar"></img> -->
                                   </td>
                            </tr>
                            <tr>
                                   <td>
                                         Old Registration Code
                                         
                                   </td>
                                   <td>
                                          <input type="text" name="txtAlias"
                                          onkeypress="return numbersonly2(event,this);"
                                                 maxlength="20" size="20"/>
                                         
                                   </td>
                            </tr>
                           
                           
                           
                     </table>
                     <table cellspacing="3" cellpadding="2" width="92%"
                            align="center">
                            <tr>
                                   <td class="tdH">
                                          <center>
                                                 <input type="submit"
                                                        name="cmdSubmit"
                                                        value="Submit"/>
                                                 <input type="button"
                                                        name="cmdCancel"
                                                        value="Cancel"
                                                        onclick="self.close();"/>
                                          </center>
                                   </td>
                            </tr>
                     </table>
                     <%
          statement.close();
          connection.close();
          %>
              </form></body>
</html>