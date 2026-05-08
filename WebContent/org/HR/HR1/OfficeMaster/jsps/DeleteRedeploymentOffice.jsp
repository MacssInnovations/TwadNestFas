<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Delete Redepolyment of an office</title>    
    <!--<script type="text/javascript" src="../../../../Library/scripts/selectOffice.js">
    </script>-->
    <script type="text/javascript" src="../scripts/DeleteselectAttachedOffice.js">
    </script>
    <script type="text/javascript" src="../scripts/DeleteRedeploymentOffice.js">
    </script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
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
 <%
               /* System.out.println("hai");
                UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                System.out.println("emp"+empProfile);
                int Emp_Id=empProfile.getEmployeeId();
                String Level=empProfile.getOfficeLevel();
                
                System.out.println("the emp id is---->>   "+Emp_Id);
                System.out.println("the Level is---->>   "+Level);*/
%>
  <form name="frmClosure" method="POST" action="../../../../../DeleteRedeploymentServlet.con" onsubmit="return nullCheckRedeployment()">
      <table width="100%" align="center">
        <tr>
            <td class="tdH">
                <center><b>Delete Redeployment of an Office</b></center>
            </td>
        </tr>
        <tr>
            <td>
                <table cellspacing="3" cellpadding="1" width="100%">
                <tr>
                  <td width="36%">
                    Existing Office&nbsp;Id  
                    <label style="color:rgb(255,0,0);">*</label>
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
                                                <input type="text" name="txtOffice_Id" maxlength="4" size="6" onchange="loadOffice(document.frmClosure.txtOffice_Id.value,'nothing');OfficeLevel();existingOffice();checkControlId();return checkoffice()" onkeypress="return  numbersonly1(event,this)"/>
                                                       <img src="../../../../../images/c-lovi.gif" onclick="jobpopup()" alt=""></img>
                                            </td>
                                           <!-- <td>
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
                    Name of the Office                     
                  </td>
                  <td width="64%">
                    <input type="text" name="txtOfficeName" size="45"
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
                    <td>Existing Controlling Office Id
                    </td>
                    <td>
                        <input type=text name="txtcontrolling" id="txtcontrolling" disabled>
                    </td>
                </tr>
                <tr>
                    <td>Existing Controlling Office Name
                    </td>
                    <td>
                        <input type=text name="txtcontrollingname" id="txtcontrollingname" size="50" disabled>
                    </td>
                </tr>
                <tr>
                    <td>Existing Controlling Office Address</td>
                    <td>
                        <textarea name="txtcontrollingaddress" cols="25" rows="4" disabled></textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan=2 class="tdH">
                        <b>Details of New Office</b>
                    </td>
                   </tr>
                   <tr>
                        <td>New Office Id</td>
                        <td><input type=text name="txtnewOffice" disabled>(System Generated)
                   </tr>
                   <tr>
                <td>New Office Name <label style="color:rgb(255,0,0);">&nbsp;</label></td>
                <td>
                    <input type=text name="txtNewOfficeName" size="40" onclick="return officeCheck()" disabled>
                </td>
                </tr>
                <tr>
                <td>New Office Short Name <label style="color:rgb(255,0,0);">&nbsp;</label></td>
                <td>
                    <input type=text name="txtNewOfficeShortName" size="40" onclick="return officeCheck()" disabled>
                </td>
                </tr>
                
                <!--<tr>
                    <td>Name of the Office<label style="color:rgb(255,0,0);">
                    &nbsp;*
                  </label></td>
                    <td>
                    <input type=text name="txtNewOfficeName" id="txtNewOfficeName" size="40">
                    </td>
                </tr>
                <tr>
                    <td>New Office Address</td>
                    <td>
                    <textarea name="txtNewOfficeAddress" id="txtNewOfficeAddress" rows="5" cols="20">
                    </textarea>
                    </td>
                </tr>-->
                
                <tr>
                  <td>
                    New Controlling Office&nbsp;Id  
                    <label style="color:rgb(255,0,0);">&nbsp;</label>
                  </td>
                  <td>                  
                  <table>
                                        <tr>
                                            <td></td>
                                            <td>Office Level</td>
                                            <td><div id="divType1" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType2" style="visibility:hidden">Select Office</div></td>
                                        </tr>
                                        <tr>                                            
                                            <td>                                            
                                            <input type="text" name="txtAttachedOfficeID" maxlength="6" size="6"
                                                   onchange="loadOffice1(document.frmClosure.txtAttachedOfficeID.value,'attach');return checkAttachedoffice()" onclick="return officeCheck()" onkeypress="return  numbersonly1(event,this)" disabled/>
                                            </td>
                                            <td>
                                                <SELECT size=1
                                                        name="cmbAttachedOfficeLevel" id="cmbAttachedOfficeLevel"
                                                        onchange="getAttachOfficesByLevel(this.form.name)" onclick="return officeCheck()" disabled>   
                                                <option value=0>
                                                        ----Select
                                                        OfficeLevel----
                                                    </option>
                                                <%
                                                     /* try
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
                                                      } */     
                                                %>
                                              </SELECT>
                                              </td>
                                              <td>
                                                <select name="cmbAttachOfficeType" style="visibility:hidden"
                                                        onchange="getAttachOfficesByType(this.form.name)">
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
                                                <select name="cmbSelectAttachOffice" id="cmbSelectAttachOffice" style="visibility:hidden" 
                                                        onchange="selectAttachOffice(this.form.name,'txtAttachedOfficeID');loadOffice1(document.frmClosure.txtAttachedOfficeID.value,'attach');">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>
                                        </tr>
                        </table>
                                        </td>
                                    </tr>
                
                
               <!-- <tr>
                    <td  width="36%">New Controlling Office Id<label style="color:rgb(255,0,0);">
                     &nbsp;*
                  </label></td>
                    <td width="64%">
                    <SELECT size=1 name="cmbLevelId" id="cmbLevelId">   
                                            <option value="0">
                                                       ----Select OfficeLevel----
                                                  </option>
                                             
                                          </SELECT>
                    </td>
                </tr>-->
                <tr>
                <td>New Controlling Office Name </td>
                <td>
                    <input type=text name="txtNewControllingOfficeName" size="50" disabled>
                </td>
                </tr>
                <tr>
                    <td>New Controlling Office Address</td>
                    <td>
                        <textarea rows="5" cols="25" name="txtNewControllingOfficeAddress" disabled></textarea>
                    </td>
                </tr>
                <tr>
                    <td>New Nature of Work <label style="color:rgb(255,0,0);">
                    &nbsp;
                  </label> </td>
                    <td>
                    <select name="cmbSecondaryID" id="cmbSecondaryID" onclick="return officeCheck()" disabled>                                        
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
                  <td  width="36%">
                    Date of Redeployment  
                  <label style="color:rgb(255,0,0);">
                    &nbsp;
                  </label>
                </td>
                  <td width="64%">
                    <input type="text" name="txtDateOfRedepolyment" size="10" maxlength="10"
                           onFocus="javascript:vDateType='3'"  onkeypress="return  calins(event,this)" onblur="checkdt(this)" onclick="return officeCheck()" disabled/>        
                           <!--<img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmClosure.txtDateOfRedepolyment);" alt="Show Calendar"></img>-->
                  </td>
                </tr>
                
                <tr>
                    <td>
                        Employees Relieval Date from the Existing Office
                    </td>
                    <td>
                        <input type=text name="txtDateOfRelieval" maxlength="10" onFocus="javascript:vDateType='3';return officeCheck()"  onkeypress="return  calins(event,this)" onblur="return checkdt(this)"  disabled> <!--checkdt() & calins() in comJS.js **** -->
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
                        <input type=text name="txtDateOfJoining" maxlength="10" onFocus="javascript:vDateType='3';return officeCheck()"  onkeypress="return  calins(event,this)" onblur="return checkdt(this)"  disabled>
                        <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmNomenClature.txtDateOfJoining);" alt="Show Calendar"></img>
                        <input type="radio" name="rad_Joining" id="rad_Relieval" value="FN" checked />FN &nbsp;&nbsp;&nbsp;&nbsp; 
                        <input type="radio" name="rad_Joining" id="rad_Relieval" value="AN" />AN
                    </td>
                </tr>
                <tr>
               
                <tr>
                
                    <td>Remarks</td>
                    <td>
                    <textarea cols="20" rows="5" name="txtRemarks" onclick="return officeCheck()" disabled></textarea>
                    </td>
                </tr>
              </table>
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
                            <tbody id="tblList" name="tblList">
                                </tbody>
                        </tr>
                        
                            
                        </table>
                        </div>
                    </td>
             </tr>
        <tr>
        
        <td align="center" class="tdH">
            <input type="submit" value="Delete" name=submit>&nbsp;&nbsp;
            <input type="RESET" value=" Clear All " name="cmdClear" onclick="funclear()">
            <input type="button" value="Exit" onclick="closeWindow()">
        </td>
        </tr>
      </table>
      
    </form>
  </body>
</html>