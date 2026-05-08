<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>CreateNomenClatureChange</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/CreateNomenClatureChange.js"></script>
   <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/NomenclatureDateCheck.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent',''); 
                    window.close(); 
                    var w=window.open(window.location.href,"_self");
                    w.close();
                    window.opener.focus();
                }
                function toExit()
                {
                      //window.close();
                    var w=window.open(window.location.href,"_self");
                    w.close();
                }
    </script>
    <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
    
  </head>
  <body class="table">
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
            connection.clearWarnings();
       }
       catch(SQLException e)
       {
              //System.out.println("Exception in creating statement:"+e);
       }          
  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  }  
 %>
  <form action="../../../../../CreateNomenClature.con" name="frmNomenClature" method="post" onsubmit="return nullCheck()">
  <table width="100%" align="center">
        <tr>
            <td class="tdH">
                <center><b>Office Nomenclature Change</b></center>
            </td>
        </tr>
        <tr>
            <td class="tdH">
                <b>Existing Office Details</b>
            </td>
        </tr>
        <tr>
            <td>
                <table cellspacing="3" cellpadding="1" width="100%">
                <tr>
                  <td width="36%">
                    Office&nbsp;ID  
                    <label style="color:rgb(255,0,0);">&nbsp;*</label>
                  </td>
                  <td>
                    <input type=text name="txtOffice_Id" maxlength="4" onchange="loadOffice(this.value)" onkeypress="return numbersonly1(event,this)" size="10">
                    <img src="../../../../../images/c-lovi.gif" alt="" onclick="jobpopup()" ></img>
                  </td>
                  
                </tr>
                <tr>
                    <td>
                    Office Name:
                  </td>
                  <td>
                    <input type=text name="txtOfficeName" size="40"  disabled>
                  </td>
                </tr>
                <tr>
                    <td>
                        Office Address:
                    </td>
                    <td>
                        <textarea rows="5" cols="25" id="txtExistAddress" name="txtExistAddress" disabled></textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                    Date of Formation:
                    </td>
                    <td>
                        <input type=text name="txtDateOfFormation" size=10 disabled>
                        <input type=hidden name="HDateOfFormation" size=10>
                    </td>
                </tr>
                
                <tr>
                    <td class="tdH" colspan="3">
                        <b>Existing Controlling Office Details</b>
                    </td>
                </tr>
                <tr>
                    <td>
                        Controlling Office Id:
                    </td>
                    <td>
                        <input type=text name="txtconOfficeId" size=10 readonly>
                    </td>
                </tr>
                <tr>
                    <td>
                        Controlling Office Name:
                    </td>
                    <td>
                        <input type=text name="txtconOfficeName" size="40" readonly>
                    </td>
                </tr>
                <tr>
                    <td> 
                        Controlling Office Address:
                    </td>
                    <td>
                        <textarea rows="5" cols="25" id="txtconOfficeAddress" name="txtconOfficeAddress" readonly></textarea>
                    </td>
                </tr>
                <tr>
                    <td class="tdH" colspan="3">
                       <b> Details of Nomenclature Change</b>
                    </td>
                </tr>
                <tr>
                    <td>
                        New Office Id:
                    </td>
                    <td>
                        <input type=text size=10 readonly style="background-color: #ececec">(System Generated)
                    </td>
                </tr>
                <tr>
                    <td>
                        New Office Name:<label style="color:rgb(255,0,0);">&nbsp;*</label>
                    </td>
                    <td>
                        <input type=text name="txtNewOfficeName" id="txtNewOfficeName" size="40" onfocus="return officeCheck()">
                   
                    </td>
                </tr>
                <tr>
                    <td>
                        New Short Office Name:<label style="color:rgb(255,0,0);">&nbsp;*</label>
                    </td>
                    <td>
                        <input type=text name="txtNewShortName" id="txtNewShortName" size="40" onfocus="return officeCheck()">
                    </td>
                </tr>
                <tr>
                    <td>
                        New Work Nature:<label style="color:rgb(255,0,0);">&nbsp;*</label>
                    </td>
                    <td>
                        <select name="cmbSecondaryID" id="cmbSecondaryID" onfocus="return officeCheck()">                                        
                        <option value="0">
                                   ----Select Work Nature----
                              </option>
                        <%
                              try
                              {
                                results=statement.executeQuery("select * from COM_MST_WORK_NATURE"); 
                                while(results.next()) 
                                {
                                    out.print("<option value='" + results.getString("Work_Nature_Id") + "'>" + results.getString("Work_Nature_Desc") + "</option>");                      
                                }
                                results.close();
                              }
                              catch(Exception e)
                              {}      
                        %>                 
                    </select> 
                    </td>
                </tr>
                <tr>
                    <td>
                        Date of Nomenclature Change:<label style="color:rgb(255,0,0);">&nbsp;*</label>
                    </td>
                    <td>
                        <input type=text name="txtDateOfNomen" id="txtDateOfNomen" maxlength="10" onFocus="javascript:vDateType='3';return officeCheck()"  onkeypress="return  calins(event,this)" onblur="return check1();checkdt(this)" >
                        <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmNomenClature.txtDateOfNomen);" alt="Show Calendar"></img>
                    </td>
                </tr>
                <tr>
                    <td>
                        Employees Relieval Date from the Existing Office
                    </td>
                    <td>
                        <input type=text name="txtDateOfRelieval" id="txtDateOfRelieval"  maxlength="10" onFocus="javascript:vDateType='3';return officeCheck()"  onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onchange="checkDateWithinRange(this.value,'>=',document.frmNomenClature.txtDateOfNomen.value,'<=','sys');"> <!--checkdt() , calins() in comJS.js  & checkDateWithinRange() is inline/ refer common files**** -->
                        <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmNomenClature.txtDateOfRelieval);" alt="Show Calendar"></img> <!--CalenderControl.js   NOT in library-->
                        <input type="radio" name="rad_Relieval" id="rad_Relieval" value="FN" checked />FN &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input type="radio" name="rad_Relieval" id="rad_Relieval" value="AN" />AN
                    </td>
                </tr>
                <tr>
                    <td>
                        Employees Joining Date in the New Office
                    </td>
                    <td>
                        <input type=text name="txtDateOfJoining" id="txtDateOfJoining"  maxlength="10" onFocus="javascript:vDateType='3';return officeCheck()"  onkeypress="return  calins(event,this)" onblur="return checkdt(this)" >
                        <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmNomenClature.txtDateOfJoining);" alt="Show Calendar"></img>
                        <input type="radio" name="rad_Joining" id="rad_Joining" value="FN" checked />FN &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input type="radio" name="rad_Joining" id="rad_Joining" value="AN" />AN
                    </td>
                </tr>
                <tr id="divcontrol" style="display:none">
                        <td colspan="3">
                        <div>
                        <table id="Existing" border="1" width="100%" style="font-family:Times New Roman;">
                        <tr>
                              <td colspan="4" class="tdH">
                              <b>Offices&nbsp;Under&nbsp;the&nbsp;Control&nbsp;of&nbsp;above&nbsp;office</b>
                               </td>
                        </tr>
                                
                        <tr>
                                        <th>Office Id</th>
                                        <th>Office Name</th>
                                        <th>Office Address</th>
                                        <th>District</th>
                                        
                                        
                         </tr>
                        <tr>
                            <tbody id="tblList">
                                </tbody>
                        </tr>
                        
                            
                        </table>
                        </div>
                    </td>
             </tr>
                <tr>
                        <td align="center" class="tdH" colspan="3">
                            <input type="submit" value="Confirm NomenClature" name=submit>
                            <input type="RESET" value=" Clear All " name="cmdClear" onclick="funclear()">
                            <input type="button" value="Exit" onclick="closeWindow()">
                        </td>
                </tr>
                </table>
             </td>
        </tr>
    </table>
  </form>
  </body>
</html>