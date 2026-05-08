

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Delete Closure of an office</title>    
    <!--<script type="text/javascript" src="../../../../Library/scripts/selectOffice.js">
    </script>
    <script type="text/javascript" src="../scripts/selectAttachedOffice.js">
    </script>-->
    <script type="text/javascript" src="../scripts/DeleteClosureOfOffice.js">
    </script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
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
  <form name="frmClosure" method="POST" action="../../../../../DeleteServletClosureOfOffice.con" onsubmit="return nullCheckMaster()">
      <table width="100%" align="center">
        <tr>
            <td class="tdH">
                <center><b>Delete Closure Of An Office</b></center>
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
                  <td width="64%">
                    <table>
                                        <!--<tr>
                                            <td>&nbsp;</td>
                                            <td>Office Level</td>
                                            <td><div id="divType1" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType2" style="visibility:hidden">Select Office</div></td>
                                        </tr>-->
                                        <tr>                                            
                                            <td>
                                                <input type="text" name="txtOffice_Id" maxlength="4" size="6" onchange="loadOffice(document.frmClosure.txtOffice_Id.value,'nothing');checkControlId();checkclosure()" onkeypress="return numbersonly1(event,this)"/>
                                                       <img src="../../../../../images/c-lovi.gif" alt="" onclick="jobpopup()" ></img>
                                            </td>
                                            <!--<td>
                                                <SELECT size=1 name=cmbOfficeLevel onchange="getOfficesByLevel(this.form.name)">   
                                                <option value=0>
                      ----Select OfficeLevel----
                    </option>
                                                <%
                                                      try
                                                      {
                                                        results=statement.executeQuery("select * from COM_MST_OFFICE_LEVELS"); 
                                                        while(results.next()) 
                                                        {
                                                            out.print("<option value='" + results.getString("Office_Level_Id") + "'>" + results.getString("Office_Level_Name") + "</option>");                      
                                                        }
                                                        results.close();
                                                      }
                                                      catch(Exception e)
                                                      {                        
                                                      }      
                                                %>
                                              </SELECT>
                                              </td>
                                              <td>
                                                <select name="cmbOfficeType" style="visibility:hidden" onchange="getOfficesByType(this.form.name)">
                                                    <option value=0>
                                                        ----Select Office
                                                        Type----
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
                                            <td>
                                                <select name="cmbSelectOffice" id="cmbSelectOffice" style="visibility:hidden" 
                                                        onchange="selectOffice(this.form.name,'txtOffice_Id');loadOffice(document.frmClosure.txtOffice_Id.value,'nothing');">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>-->
                                        </tr>
                                        </table>
                  </td>
                </tr>
                <tr>
                  <td  width="36%">
                    Name Of The Office To Be Closed                    
                  </td>
                  <td width="64%">
                    <input type="text" name="txtOfficeName" size="40"
                           disabled="disabled"/>                    
                  </td>
                </tr>
                <tr>
                    <td  width="36%">Office Address</td>
                    <td width="64%">
                        <textarea name="txtOfficeAddress" cols="25" rows="4"
                                  disabled="disabled"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>Date of Formation
                    </td>
                    <td>
                        <input type=text name="txtDateOfFormation" id="txtDateOfFormation" disabled>
                        <input type=hidden name="HDateOfFormation" id="HDateOfFormation">
                    </td>
                </tr>
                <tr>
                  <td  width="36%">
                    Date of Closure of this Office  
                  <label style="color:rgb(255,0,0);"/>
                </td>
                  <td width="64%">
                    <input type="text" name="txtClosureDate" size="10" maxlength="10"
                           onFocus="return officeCheck();javascript:vDateType='3'" onBlur="check1();return checkdt(this);" onkeypress="return  calins(event,this)" disabled/>                   
                           <!--<img src="../../../../../images/calendr3.gif"  onclick="showCalendarControl(document.frmClosure.txtClosureDate);" alt="Show Calendar"></img>-->
                  </td>
                </tr>
                <tr>
                    <td  width="36%">Remarks</td>
                    <td width="64%">
                        <textarea name="txtRemarks" cols="25" rows="4" onfocus="return officeCheck()" disabled></textarea>
                    </td>
                </tr>
              </table>
          </td>
        </tr>
        <tr id="divcontrol" style="display:none">
            <td>
                <div>
                <table id="Existing" border="1" width="100%" style="font-family:Times New Roman;">
                <tr>
                      <td colspan="4" class="tdH">
                      <b>Offices Under control of above Office</b>
                       </td>
                </tr>
                        
                <tr>
                                <th>Office Id</th>
                                <th>Office Name</th>
                                <th>Office Address</th>
                                <th>
                    District
                  </th>
                                
                                
                 </tr>
                <tr>
                    <tbody id="tblList" name="tblList">
                        </tbody>
                </tr>
                
                    
                </table>
                </div>
            </td>
        </tr>
        <tr>
        <td align="center" class="tdH">
            <input type="submit" value="Delete" name=submit>
            <input type="reset" value="Clear All" name="cmbClear" id="cmbClear" onclick="funclear()">
            <input type="button" value="Exit" onclick="closeWindow()">
        </td>
        </tr>
      </table>
      
    </form>
  </body>
</html>