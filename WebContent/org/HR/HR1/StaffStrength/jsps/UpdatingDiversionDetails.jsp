<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
    <head>
        <meta http-equiv="Content-Type"
              content="text/html; charset=windows-1252"/>
        <title>Create Diversion Details</title>
        <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
        <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
        <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
        <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
        <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
        <script type="text/javascript" src="../scripts/UpdateDiversionDetails.js"></script>
        <script type="text/javascript" src="../scripts/UpdateDiversionDetailAjax.js"></script>
        <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                function getValsFromSession()
                {
                //alert('hai');
                    document.frmStaffStrength.txtOffice_Id.disabled=false;
                    document.frmStaffStrength.txtOffice_Id.focus();
                    loadfyr(); 
                    
                }
          </script>
    </head>
    <body class="table" onload="getValsFromSession()"><%
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
  
  %><form action="../../../../../CreateUpdateDiversionServlet.con"
          name="frmStaffStrength" method="post" onsubmit="return nullCheck()">
           
            <table cellspacing="1" cellpadding="3" width="100%" border="1"
                   class="bgbody">
                <tr>
                    <td class="tdH" colspan="5">
                        <center>
                            <h2>Create Diversion Details</h2>
                        </center>
                    </td>
                </tr>
                <tr>
                    <td colspan="5">
                        <table cellspacing="3" cellpadding="1" width="100%">
                            <tr>
                                <td>Office Id</td>
                                <%
                                            System.out.println("hai");
                                      HttpSession   session=request.getSession();
                                            UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                                            System.out.println("emp"+empProfile);
                                            int Emp_Id=empProfile.getEmployeeId();
                                            String Level=empProfile.getOfficeLevel();
                                            System.out.println("the Short Name:"+empProfile.getOfficeShortName());
                                            System.out.println("the emp id is---->>   "+Emp_Id);
                                            System.out.println("the Level is---->>   "+Level);
                                           // int Emp_Id=11263;
                                            int Office_Id=0;
                                             try
                                               {
                                                    results=statement.executeQuery("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID="+Emp_Id); 
                                                    if(results.next()) 
                                                    {
                                                       Office_Id=results.getInt("OFFICE_ID");
                                                    System.out.println("the office id is---->>   "+Office_Id);
                                                    }
                                                    results.close();
                                               }
                                            catch(Exception e)
                                            {
                                                    System.out.println("exception occured : " + e);
                                            }    
                                 %>
                                <td colspan="4">
                                    <input type="text" name="txtOffice_Id"
                                           onfocus="loadOffice(document.frmStaffStrength.txtOffice_Id.value)"
                                           value="<%=Office_Id%>"
                                           disabled="disabled"></input>
                                    <input type="hidden" name="txtOffice_Id1" value="<%=Office_Id%>"></input>
                                </td>
                            </tr>
                            <tr>
                                <td>Office Name</td>
                                <td colspan="4">
                                    <input type="text" name="txtOfficeName"
                                           size="45" disabled="disabled"></input>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdH" colspan="5">
                                    <b>Diversion Details</b>
                                </td>
                            </tr>
                            <tr>
                                <td>Order Id</td>
                                <td colspan="4">
                                    <input type=text name="txtOrderId" disabled style="background-color: #ececec">(System Generated)
                                </td>
                            </tr>
                            <tr>
                <td>
                    Financial Year<label style="color:rgb(255,0,0);"> &nbsp;*</label>
                </td>
                <td>
                    <select name="cmbFinancialYear" id="cmbFinancialYear" tabindex="1">
                    <option value=0>---Select Financial Year---</option>
                    </select>
                </td>
            </tr>
                            <tr>
                                <td>
                                    Diversion Order Date:
                                    
                                </td>
                                <td colspan="4">
                                    <input type="text" name="txtDoD"
                                           maxlength="10" size="10"
                                           onfocus="javascript:vDateType='3'"
                                           onkeypress="return  calins(event,this)"
                                           onblur="return checkdt(this);"></input>
                                    <img src="../../../../../images/calendr3.gif"
                                         onclick="showCalendarControl(document.frmStaffStrength.txtDoD);"
                                         alt="Show Calendar"></img>
                                </td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td>Office Level</td>
                                <td>
                                    <div id="divType1"
                                         style="visibility:hidden">Office Type</div>
                                </td>
                                <td>
                                    <div id="divType2"
                                         style="visibility:hidden">Select Office</div>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Diversion From Office Id:
                                    <label style="color:rgb(255,0,0);">&nbsp;*</label>
                                </td>
                                <td>
                                    <input type="text"
                                           name="txtdiversionfromoffice"
                                           size="4" 
                                           onchange="PostRank(this.value);loadofficeaddress(this.value);" onkeypress="return numbersonly1(event,this)" maxlength=4></input>
                                    <input type="hidden"
                                           name="txtHdiversionfromoffice"></input>
                                </td>
                                <td>
                                    <select name="cmbFromOfficeId"
                                            id="cmbFromOfficeId"
                                            onchange="getOfficesByLevel()">
                                        <option value="0">---Select Office Id ---</option>
                                    </select>
                                </td>
                                <td>
                                    <select name="cmbOfficeType"
                                            style="visibility:hidden"
                                            onchange="getOfficesByType()">
                                        <option value="0">----Select Office
                                                          Type----</option>
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
                                    <select name="cmbSelectOffice"
                                            id="cmbSelectOffice"
                                            style="visibility:hidden"
                                            onchange="selectControllineOffice()">
                                        <option value="0">----Select Office----</option>
                                    </select>
                                </td>
                            </tr>
                            <!--<tr>
                                <td>
                                    Post Category
                                    <label style="color:rgb(255,0,0);">&nbsp;*</label>
                                </td>
                                <td colspan="4">
                                    <select name="cmbPostCategory"
                                            id="cmbPostCategory" tabindex="5">
                                        <option value="0">---Select
                                                          PostCategory---</option>
                                        <%
                                                    /*try
                                                    {
                                                      results=statement.executeQuery("select * from HRM_MST_EMPLOYMENT_STATUS"); 
                                                      while(results.next()) 
                                                      {
                                                          out.print("<option value='" + results.getString("EMPLOYMENT_STATUS_ID") + "'>" + results.getString("EMPLOYMENT_STATUS") + "</option>");                      
                                                      }
                                                      results.close();
                                                    }
                                                    catch(Exception e)
                                                    {
                                                    } */ 
                                         %>
                                    </select>
                                </td>
                            </tr>
                            <TR>
                            <td>Order Id</td>
                             <td><input type=text name=txtorderid disabled></td>   
                            </TR>-->
                            
                            <!--<tr>
                                <td colspan="5">
                                    <table name="Existing" id="Existing"
                                           border="1" width="100%"
                                           style="font-family:arial;display:none">
                                        <tr>
                                            <td colspan="5">
                                                <b>Existing Diversion Details </b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Sl.No</th>
                                            <th>Office from Which
                                                            Diverted</th>
                                            <th>Date of Diversion</th>
                                            <th>Remarks</th>
                                            
                                        </tr>
                                        <tr>
                                            <tbody id="tblList"></tbody>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td class="tdH" colspan="5" >
                                    <b>Current Diversion Details</b>
                                </td>
                            </tr>-->
                            <tr>
                                <!--<td>Office Name</td>
                                <td><input type=text name="txtOfficeNameFrom" id="txtOfficeNameFrom" disabled size="45"></td>-->
                            
                                <td>Office Name/ Address</td>
                                <td><textarea rows="5" cols="25" id="txtOfficeAddressFrom" name="txtOfficeAddressFrom" disabled></textarea></td>
                            <tr>
                            <tr>
                                <td>
                                    Name of the Post Diverted:
                                    <label style="color:rgb(255,0,0);">&nbsp;*</label>
                                </td>
                                <td colspan="4">
                                    <select name="cmbPostRank" id="cmbPostRank"
                                            onchange="noofsanction1();SanctionPost();">
                                        <option value="0">---Select Post Rank---</option>
                                    </select>
                                </td>
                            </tr>
                            <tr id="noofsantionpost" style="display:none">
                                <td>No.of Sanctioned Post</td>
                                <td>
                                    <input type="text" name="noofsanction"
                                           disabled="disabled"></input>
                                </td>
                                <td colspan="3">
                                    <table border="0">
                                        <tr>
                                            <td>Already Diverted To Other Offices</td>
                                            <td>
                                                <input type="text"
                                                       name="divertedtopost"
                                                       disabled="disabled"></input>
                                            </td>
                                            <!--<td>No.of Post Diverted From</td>
                                                <td><input type=text name="divertedfrompost" disabled></td>-->
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                         <tr>

                            <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>Office Level</td>
                                    <td>
                                        &nbsp;
                                        <div id="divType3" style="visibility:hidden">Office Type</div>
                                    </td>
                                    <td>
                                        &nbsp;
                                        <div id="divType4" style="visibility:hidden">Select
                                                                                     Office</div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Diversion To Office Id:
                                        <label style="color:rgb(255,0,0);">&nbsp;*</label>
                                    </td>
                                    <td>
                                        <input type="text" name="txtdiversiontooffice" size="4" onchange="loadofficeaddressto(this.value);officeid();return checktooff()" onkeypress="return numbersonly1(event,this)" maxlength=4></input>
                                        <input type="hidden" name="txtHdiversiontooffice"></input>
                                    </td>
                                    <td>
                                        <select name="cmbToOfficeId" id="cmbToOfficeId"
                                                onchange="getOfficesByLevel1()">
                                            <option value="0">---Select To Office Id ---</option>
                                        </select>
                                    </td>
                                    <td>
                                        <select name="cmbOfficeType1" style="visibility:hidden"
                                                onchange="getOfficesByType1()">
                                            <option value="0">----Select Office Type----</option>
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
                                        <select name="cmbSelectOffice1" id="cmbSelectOffice1"
                                                style="visibility:hidden"
                                                onchange="return selectControllineOffice1();">
                                            <option value="0">----Select Office----</option>
                                        </select>
                                    </td>
                         </tr>
                         <tr>
                                <!--<td>Office Name</td>
                                <td><input type=text name="txtOfficeNameTo" id="txtOfficeNameTo" disabled size="45"></td>-->
                            
                                <td>Office Name/ Address</td>
                                <td><textarea rows="5" cols="25" id="txtOfficeAddressTo" name="txtOfficeAddressTo" disabled></textarea></td>
                        </tr>
                            <tr>
                         
                                                        <td>
                                No. of Posts Diverted:
                                <label style="color:rgb(255,0,0);">&nbsp;*</label>
                            </td>
                            <td colspan="4">
                                <input type="text" name="txtPostDiverted" size="10"
                                       maxlength="3"
                                       onkeypress="return  numbersonly1(event,this)" onchange="return checksanction()"></input>
                            </td>
                        </tr>
                        <tr>
                            <td>Diversion Effective Date:</td>
                            <td><input type=text name="txtDEDate" size=10 maxlength=10 onfocus="javascript:vDateType='3'"
                                           onkeypress="return  calins(event,this)"
                                           onblur="return checkdt(this);">
                            <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmStaffStrength.txtDEDate);" alt="Show Calendar"></img>
                        </tr>
                        <tr>
                            <td>Diversion Period Upto:</td>
                            <td><input type=text name="txtDPDate" size=10 maxlength="10 onfocus="javascript:vDateType='3' onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                            <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmStaffStrength.txtDPDate);" alt="Show Calendar"></img>
                            
                        </tr>
                        <tr>
                            <td>Remarks</td>
                            <td colspan="4">
                                <textarea rows="5" cols="25" name="txtRemarks"></textarea>
                            </td>
                        </tr>
             </table>
                    </td>
                </tr>
            
            <!--<tr>
                <td colspan="5" class="tdH">
                    <div>
                        <table border="0">
                            <tr>
                                <td>
                                    <input type="Button" value="  Save "
                                           id="Add"
                                           onclick="callServer('Add','null')"
                                           name="cmdAdd" style="display:block"></input>
                                </td>
                                <td>
                                    <input type="Button" value=" Update"
                                           onclick="callServer('Update','null')"
                                           name="cmdUpdate"
                                           style="display:none"></input>
                                </td>
                                <td>
                                    <input type="Button" value=" Delete"
                                           id="Revoke"
                                           onclick="callServer('Delete','null')"
                                           name="cmdDelete"
                                           style="display:none"></input>
                                </td>
                                <td>
                                    <input type="Button" value="Clear All"
                                           onclick="clearAll()"
                                           name="cmdClearAll"
                                           style="display:block"></input>
                                </td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="5">
                    <table name="Existingto" id="Existingto" border="1"
                           width="100%" style="font-family:arial;display:block">
                        <tr>
                            <td colspan="4">
                                <b>Current Diversion Details </b>
                            </td>
                        </tr>
                        <tr>
                            <th>View</th>
                            <th>Office to Which Diverted</th>
                            <th>Post Diverted</th>
                            <th>Remarks</th>
                        </tr>
                        <tr>
                            <tbody id="tblListto"></tbody>
                        </tr>
                    </table>
                </td>
            </tr>-->
            <tr>
                <td colspan="5" class="tdH">
                    <center>
                        <input type="submit" value="Submit" name="cmbSubmit"
                               id="cmbSubmit"></input>
                        <input type="reset" value="ClearAll" onclick="clear1()"></input>
                        <input type="button" value="Exit"
                               onclick="closeWindow()"></input>
                    </center>
                </td>
            </tr>
            </table>
        </form></body>
</html>