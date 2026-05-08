
<!--
    File Name     : IntranetMajorSystems.jsp
    Purpose       : To create form that allows us add,modify and delete records residing in database
    References    : BenefitAjax.js,BenefitValidations.js,sample2.css
    Servlet Ref.  : ServletBenefitMaster.java
-->

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <title>Validate Wings Details</title>
    
          <!--<link href="../../../../../css/yellow.css" rel="stylesheet" media="screen"/>-->
          <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
          <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
          <script type="text/javascript" src="../scripts/ValidateWings.js"></script>
          <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
          <!--<script type="text/javascript" src="../scripts/controllingOfficeWing.js"></script>-->
          <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
          <script type="text/javascript"       src="../scripts/CalendarControl.js"></script>
          <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
          </script> 
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
 <form name="frmOffice" method="Post" action="../../../../../ValidateWingsDetails.con?command=Add" >
        <div id="dhtmltooltip"></div>
    <script type="text/javascript" src="test.js"></script>
               
                <table  cellspacing="1" cellpadding="3"  width="100%" border="1" class="table">
                    <tr>
                        <td class="tdH">
                            <center><h2>Validate Wings Details</h2></center>
                        </td>
                    </tr>
                    <tr>
                        <td>                        
                              <table  cellspacing="3" cellpadding="1"  width="100%" >
                                  <tr>
                                      <td>Office_Id</td>
                                      <td>
                                          <table>
                                        <!--<tr>                                       
                                            <td></td>
                                            <td>Office Level</td>
                                            <td><div id="divType1" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType2" style="visibility:hidden">Select Office</div></td>
                                        </tr>-->
                                        <tr>
                                            <td>
                                                <input type="text" name="txtOffice_Id"  onkeyup="isInteger(this,event)" id="txtOffice_Id" onchange="callServer1('Load','null');checkofficestatus();officelevel();" tabindex="1" size="4" maxlength="4" onkeypress="return  numbersonly1(event,this)"><img src="../../../../../images/c-lovi.gif" onclick="jobpopup()" alt=""></img>
                                            </td>
                                            <!--<td>
                                                <SELECT size=1 name=cmbControllingLevel onchange="getOfficesByLevel()">   
                                                <option value>
                                                        ----Select
                                                        OfficeLevel----
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
                                                <select name="cmbOfficeType" style="visibility:hidden" onchange="getOfficesByType()">
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
                                                <select name="cmbSelectOffice" id="cmbSelectOffice"  style="visibility:hidden" onchange="selectControllineOffice('office')">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>-->
                                        </tr>
                                        </table>
                                              
                                      </td>
                                  </tr>
                                  <tr>
                                      <td><font color="#808080">Name of The Office</font></td>
                                      <td>
                                      <input type="text" name="txtOffice_Name" id="txtOffice_Name" size="45" disabled/>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td><font color="#808080">Office Address</font></td>
                                      <td>
                                            <!--<input type="text" name="txtOffice_Address1" id="txtOffice_Address1" disabled>-->
                                            <textarea rows="5" cols="25" name="txtOffice_Address1" id="txtOffice_Address1" disabled></textarea>
                                </td>
                                  </tr>
                                  <!--<tr>
                                      <td><font color="#808080">Office Address2</font></td>
                                      <td>
                                            <input type="text" name="txtOffice_Address2" id="txtOffice_Address2" disabled>
                                </td>
                                  </tr>
                                  <tr>
                                      <td><font color="#808080">Office Address3</font></td>
                                      <td>
                                            <input type="text" name="txtOffice_Address3" id="txtOffice_Address3" disabled>
                                </td>
                                  </tr>-->
                                  
                                  <tr>
                                      <td><font color="#808080">
                                      Districts</font></td>
                                      <td>
                                      <select name="cmbDistrict1" id="cmbDistrict1" disabled>
                                      <option>--Select District--</option>
                                      <%
                                                        try
                                                        {
                                                          results=statement.executeQuery("select * from COM_MST_DISTRICTS order by District_Name"); 
                                                          while(results.next()) 
                                                          {
                                                              out.print("<option value='" + results.getInt("District_Code") + "'>" + results.getString("District_Name") + "</option>");                      
                                                          }
                                                          results.close();
                                                        }
                                                        catch(Exception e)
                                                        {
                                                        }  
                                        %>

                                      </select>
                                      </td>
                                  </tr>
                                  <tr>
                                        <td>Date of Formation</td>
                                        <td>
                                            <input type=text name="txtDateOfFormation" disabled>
                                            <input type=hidden name="txtHDateOfFormation" >
                                        </td>
                                  </tr>
                                  <!--<tr>
                                      <td><font color="#808080">
                                        Phone No</font>
                                     </td>
                                      <td>
                                        <input type=text name="txtPhoneNo" id="txtPhoneNo" disabled>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td><font color="#808080">
                                        Fax No</font>
                                      </td>
                                      <td>
                                        <input type=text name="txtFaxNo" id="txtFaxNo" disabled>
                                      </td>
                                  </tr>-->
                                  <tr>
                                    <td colspan=2 class="tdH">
                                        <b>Details of Wings in Office</b>
                                    </td>
                                   </tr>
                                   <%
                                    try
                                    {
                                        //results=statement.executeQuery("select max(OFFICE_WING_SINO) from com_office_wings where office_id="+
                                    }catch(Exception e)
                                    {
                                        System.out.println("The Exception is:"+e);
                                    }
                                    
                                   %>
                                   <!--<tr>
                                      <td>
                                        Sl No
                                      </td>
                                      <td>
                                        <input type=text name="txtSl_No" id="txtSl_No" tabindex="2" disabled>
                                        
                                      </td>
                                  </tr>-->
                                  <tr>
                                      <td>
                                        Wing Name<label style="color:rgb(255,0,0);">*</label>
                                      </td>
                                      <td>
                                        <input type=text name="txtWing_Name" id="txtWing_Name" tabindex="3" onfocus="return officeCheck()">
                                      </td>
                                  </tr>
                                  <tr>
                                      <td>
                                        Head Of The Wing<label style="color:rgb(255,0,0);">*</label>
                                      </td>
                                      <td>
                                        <select name="cmbWing_Head" id="cmbWing_Head" tabindex="4" onfocus="return officeCheck()">
                                      <option value=0>--Select Wing Head--</option>
                                      <%
                                                        try
                                                        {
                                                          results=statement.executeQuery("select designation,designation_id from HRM_MST_WING_HEAD_DESIG"); 
                                                          while(results.next()) 
                                                          {
                                                              out.print("<option value='" + results.getInt("designation_ID") + "'>" + results.getString("designation") + "</option>");                      
                                                          }
                                                          results.close();
                                                        }
                                                        catch(Exception e)
                                                        {
                                                        }  
                                        %>

                                      </select>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td>
                                        Date Created<label style="color:rgb(255,0,0);">*</label>
                                      </td>
                                      <td>
                                        <input type=text name="txtDateCreated" id="txtDateCreated" maxlength="10" tabindex="5" onFocus="return officeCheck();javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt1(this);">
                                        <img src="../../../../../images/calendr3.gif"
                           onclick="showCalendarControl(document.frmOffice.txtDateCreated);"
                           alt="Show Calendar"></img>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td>
                                        Phone No
                                      </td>
                                      <td>
                                        <input type=text name="txtPhone_No" id="txtPhone_No" onkeypress="return  numbersonly1(event,this)" tabindex="6" maxlength="10" onchange="return checkphone();" onfocus="return officeCheck()">
                                      </td>
                                  </tr>
                                  <tr>
                                      <td>
                                        Fax No
                                      </td>
                                      <td>
                                        <input type=text name="txtFax_No" id="txtFax_No" onkeypress="return  numbersonly1(event,this)" tabindex="7" maxlength="10" onchange="return checkfax();" onfocus="return officeCheck()">
                                      </td>
                                  </tr>
                                  <tr>
                                      <td>
                                        Nature Of the Work
                                      </td>
                                      <td>
                                        <textarea rows="4" name="Work_Nature" cols="38" id="Work_Nature" tabindex="8" onfocus="return officeCheck()"></textarea>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td>
                                        Email Id
                                      </td>
                                      <td>
                                        <input type=text name="txtEmailId" id="txtEmailId" onchange="return ValidateForm()" tabindex="9" onfocus="return officeCheck()">
                                      </td>
                                  </tr>
                                  <tr>
                                      <td colspan=2 class="tdH">
                                        <div>
                                            <table border=0>
                                                <tr>
                                                    <td>
                                                      <input type="Button" value="  Save " id="Add" onclick="callServer1('Add','null')" name="cmdAdd" tabindex="10" style="display:block" onfocus="return officeCheck()"> 
                                                    </td>
                                                    <td>
                                                      <input type="Button" value=" Update" onclick="callServer1('Update','null')"  name="cmdUpdate" style="display:none">
                                                    </td>
                                                    <td>
                                                      <input type="Button" value=" Delete" id="Revoke" onclick="callServer1('Delete','null')"  name="cmdDelete" style="display:none">
                                                    </td>
                                                    <td>
                                                      <input type="Button" value="Clear All" onclick="clearAllWing()" name="cmdClearAll" style="display:block" onfocus="return officeCheck()">                                 
                                                    </td>
                                                </tr>
                                                </table>
                                          </div>
                                      </td>                              
                                  </tr>      
                                  
                                  </table>
                                  
                                   <!--<div id="mydiv" name="mydiv"> -->
                                    <table name="Existing" id="Existing"  border="1" width="100%"  style="font-family:arial;">
                                    <tr>
                                            <td colspan=8>
                                                <b>Wing Details</b>
                                            </td>
                                    </tr>
                                    
                                    <tr>
                                            <th >View</th>
                                            <!--<th>SL No</th>-->
                                            <th>Wings Name</th>
                                            <th>Head Of The Wing</th>
                                            <th>Date Created</th>
                                            <th>Phone</th>
                                            <th>Fax</th>
                                            <th>Nature Of The Work</th>
                                            <th>Email Id</th>
                                    </tr>
                                   
                                    <tbody id="tblList" name="tblList">
                                    </tbody>
                                    <tr>
                                        <td colspan=8 class="bgClass"><center>
                                            <input type=Submit value=Validate  name="cmbSubmit" id="cmbSubmit" onclick="return nullcheck()"/>
                                            <input type="RESET" value=" Clear All " name="cmdClear" onclick="clear1()">&nbsp; 
                                            <input type=button value=Cancel onclick="closeWindow();"/></center>
                                        </td>
                                    </tr>
                                  </table>
                                  
                           
                           
                        </td>                        
                    </tr>
              </table>
        </form>
  </body>
</html>

