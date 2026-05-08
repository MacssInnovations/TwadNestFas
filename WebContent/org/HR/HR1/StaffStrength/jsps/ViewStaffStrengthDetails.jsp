<!--
    File Name     : MasterBenefit.jsp
    Purpose       : To create form that allows us add,modify and delete records residing in database
    References    : BenefitAjax.js,BenefitValidations.js,sample2.css
    Servlet Ref.  : ServletBenefitMaster.java
-->

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>


<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <meta http-equiv="cache-control" content="no-cache">
      <title>View Sanctioned Staff Strength For An Office</title>    
      
          <script type="text/javascript" src="../scripts/ViewStaffStrengthDetailsAjax.js" ></script>
          <script type="text/javascript" src="../scripts/ViewStaffStrengthDetails.js" ></script>
          <script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
          <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
          <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>
          
          
          
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
                    
                    loadfyr(); 
                }
          </script> 
          <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>  
          <link href='../../../../../css/RWS_CSSColour.css' rel='stylesheet' media='screen'/>
          <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
          <style type="text/css">
                .divClass{display: none;  }                                
          </style>
    </head>
 <body class="table" onload="getValsFromSession();"> 
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
              System.out.println("Exception in creating statement:"+e);
              return;
       }          
  }
  catch(Exception e)
  {         
         System.out.println("Exception in openeing connection:"+e);
         return;
  }  
 %>        
        <table  cellspacing="1" cellpadding="3" width="100%" class="bodytext">
                    <tr>
                        <td class="tdH">
                            <center>                           
                            <h3>View Sanctioned Staff Strength for an Office</h3>                            
                            </center>
                        </td>
                    </tr>                    
              </table>
        <form name="frmOffice" method="POST" action="" onsubmit="return nullValue()">                
                <!--<div class="tab-pane" id="tab-pane-1">
                    <div class="tab-page">
                        <h2 class="tab">General Details</h2>
                        <div align="center">-->
                            <table  cellspacing="3" cellpadding="1"  width="100%" class="tab">
                            <tr>
                                <td class="tdH" colspan="3"><b>General Details</b></td>
                            </tr>
                                  <TR>
                                    <TD class="bgbody">
                                        Office Id<label style="color:rgb(255,0,0);"> &nbsp;*</label>
                                      </TD>
                                      <TD>
                                      <%
                                           /* System.out.println("hai");
                                            HttpSession session=request.getSession();
                                            UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                                            System.out.println("emp"+empProfile);
                                            int Emp_Id=empProfile.getEmployeeId();
                                            String Level=empProfile.getOfficeLevel();
                                            System.out.println("the Short Name:"+empProfile.getOfficeShortName());
                                            System.out.println("the emp id is---->>   "+Emp_Id);
                                            System.out.println("the Level is---->>   "+Level);
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
                                            } */   
                                 %>
                                <input type="text" name="txtOffice_Id" id="txtOffice_Id"  onchange="loadOffice(document.frmOffice.txtOffice_Id.value,'nothing')"  maxlength="4" size="4" onkeypress="return  numbersonly1(event,this)">
                                <input type="hidden"  name="txtOffice_Id1" id="txtOffice_Id1"  >                                          
                                <img src="../../../../../images/c-lovi.gif" alt="" onclick="jobpopup()" ></img>
                                </TD>
                                  </TR>
                                 <tr>
                                      <td><font color="#808080">Office Name</font> </td>
                                      <td style="color:rgb(255,51,102);">
                                        <input type="text" name="txt_ExtOffice_Name" id="txt_ExtOffice_Name" size="40" maxlength="60" disabled/>
                                      </td>
                                  </tr>
                                 <!--  <tr>
                                      <td rowspan="3"><font color="#808080">
                                        Office Address
                                    </font>
                                </td>
                                      <td>
                                        <input type="text" name="txt_ExtOffice_Address1" id="txt_ExtOffice_Address1" maxlength="30" size="30" disabled/>
                                      </td>
                                  </tr> 
                                  <tr>
                <td>
                    <input type="text" name="txt_ExtOffice_Address2" id="txt_ExtOffice_Address2" disabled>
                </td>
             </tr>
             <tr>
                <td>
                    <input type="text" name="txt_ExtOffice_City" id="txt_ExtOffice_City" disabled>
                </td>
            </tr>
            <tr>
                <td>
                    <font color="#808080">District</font>
                </td>
                <td>
                    <select name="cmb_ExtDistrict" id="cmb_ExtDistrict" disabled>
                    <option>----Select District----</option>
                     <%
                                                        try
                                                        {
                                                          results=statement.executeQuery("select * from COM_MST_DISTRICTS"); 
                                                          while(results.next()) 
                                                          {
                                                              out.print("<option value='" + results.getInt("District_Code") + "'>" + results.getString("District_Name") + "</option>");                      
                                                          }
                                                          results.close();
                                                        }
                                                        catch(Exception e)
                                                        {
                                                            System.out.println("exception occured : " + e);
                                                        }      
                                                  %>
                    </select>
                </td>
            </tr>-->
            <tr>
                <td>
                    Financial Year
                </td>
                <td>
                    <select name="cmbFinancialYear" id="cmbFinancialYear" onchange="callServer('TableView',null);" onfocus="return OfficeCheck()">
                    <option value=0>---Select Financial Year---</option>
                    </select>
                </td>
            </tr>
            <!--<tr>
                <td>
                    Status As On Date<label style="color:rgb(255,0,0);"> &nbsp;*</label>
                </td>
                <td>
                    <input type=text name="txtRequest_Date" maxlength="10" onFocus="javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                    <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmOffice.txtRequest_Date);" alt="Show Calendar" ></img>
                </td>
            </tr>-->
            <tr>
               <td>Remarks</td>
               <td>
                  <textarea name="txtRemarks" cols="25" rows="5" disabled></textarea>
               </td>
             </tr>
              <!--  <tr>
                <td class="tdH" colspan="3"><b>Sanctioned Post Details</b></td>
             </tr>
           <tr>
                <td>
                  Select Sanction Strength Template
                </td>
                <td>
                <select name="cmbSSTemp_Id" id="cmbSSTemp_Id" onchange="callServer('TableView',null)">
                <option value=0>--Select TemplateId--</option>
                </select> 
                </td>
            </tr>
            <tr>
                <td>Post Category 
                </td>
                <td>
                  <select name="cmbPostCategory" id="cmbPostCategory" tabindex="5" disabled>
                <option value=0>---Select PostCategory---</option>
                <%
                            try
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
                            }  
            %>
                
            </select>
                </td>
            </tr> 
            <tr>
                <td>
                 Service Grouping
                </td>
                <td>
                  <select name="cmbServiceGroup" id="cmbServiceGroup" onchange="callServer('ServiceGroup',null)" tabindex="3" disabled>
                <option value=0>----Select Service Group----</option>
                <%
                            try
                            {
                              results=statement.executeQuery("select * from HRM_MST_SERVICE_GROUP"); 
                              while(results.next()) 
                              {
                                  out.print("<option value='" + results.getInt("SERVICE_GROUP_ID") + "'>" + results.getString("SERVICE_GROUP_NAME") + "</option>");                      
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
                <td>Post Rank</td>
                <td>
                 <select name="cmbPostRank" id="cmbPostRank" tabindex="4" disabled>
                 <option value=0>--Select PostRank--</option>
                 </select>
                </td>
            </tr> 
             
            <tr>
                <td>No of Sanctioned Posts</td>
                <td>
                    <input type=text name="txtNoPost" id="txtNoPost" maxlength="4" tabindex="6" onkeypress="return  numbersonly1(event,this)" disabled/>
                </td>
            </tr>
            <tr>
            <td>No of Posts Diverted to Other</td>
            <td>
              <input type="text" name="txtNoOfPostTo" maxlength="4" size="30" onkeypress="return  numbersonly1(event,this)" tabindex="6" disabled/>
            </td>
        </tr> 
        
        <tr>
            <td>No of Posts Diverted to From</td>
            <td>
              <input type="text" name="txtNoOfPostFrom" maxlength="4" size="30" onkeypress="return  numbersonly1(event,this)"  tabindex="7" onchange="total()" disabled/>
            </td>
        </tr>
        <tr>
        <td class="td">
        Total Strength
        <td>
        <input type=text name="txtTotal" id="txtTotal" onkeypress="return  numbersonly1(event,this)" disabled>
      </tr>
        <tr>
          <td class="td">
            Remarks
          </td>
          <td>
            <textarea name="Remarks" cols="25" rows="5" disabled></textarea>
          </td>
      </tr>
      
            <!--<tr>
              <td colspan=2 class="tdH">
                <div>
                    <table border="0">
                        <tr>
                            <td>
                   <input type="Button" value="  Save " id="Add" onclick="callServer('Add','null')" name="cmdAdd" tabindex="8" style="display:block"> 
                    </td><td>
                   <input type="Button" value=" Update" onclick="callServer('Update','null')"  name="cmdUpdate" style="display:none">
                    </td><td>
                   <input type="Button" value=" Delete" id="Revoke" onclick="callServer('Delete','null')" name="cmdDelete" style="display:none">
                    </td><td>
                   <input type="Button" value="Clear All" onclick="clearAll()" name="cmdClearAll" style="display:block">
                    </td><td>
                    </tr>
                    </table>
                </div>
              </td>                              
            </tr>--> 
            <table name="Existing" id="Existing"  border="1" width="100%"  style="font-family:arial;">
                <tr>
                        <td colspan="11" class="tdH">
                            <b>Existing  Sanction Strength Details
</b>
                        </td>
                </tr>
                
                <tr>
                        <th rowspan="2">Sl No</th>
                        <th rowspan="2">Service Group</th>
                        <th rowspan="2">Post Rank</th>
                        <!--<th rowspan="2">Post Category</th>-->
                        <th rowspan="2">No Of Sanctioned Posts</th>
                        <th rowspan="2">No of Posts Diverted To</th>
                        <th rowspan="2">No of Posts Diverted From</th>
                        <th rowspan="2">Total Strength</th>
                        <th rowspan="2">Remarks</th>
                        
                </tr>
                        
                        <tr>
            <tbody id="tblList" name="tblList">
                </tbody>
        </tr>
        </table>
        <tr>
            <td colspan="11">
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Total &nbsp;&nbsp;&nbsp;&nbsp; <input type=text name=totalgrid size=4 disabled>
            </td>
        </tr>
            </table>
            
             
           <!-- <div class="tab-page">
                        <h2 class="tab">Existing Sanctioned Strength</h2>
                        <div align="center">
                            <table width="100%">
                            <tr>
                                      <td colspan="2">
                                         <label>Existing Sanctioned Strength Requirment Details</label>
                                         <div id="Sanctioned">
                                         <table cellspacing="1" cellpadding="3" width="100%" style="border-color:rgb(185,201,202); border-width:medium; border-style:groove;">
                                         
                                         
                                        <tr>
                                            <td>
                                             Service Grouping<label style="color:rgb(255,0,0);"> &nbsp;*</label>
                                            </td>
                                            <td>
                                              <select name="cmbServiceGroup" id="cmbServiceGroup" onchange="callServer('ServiceGroup',null)" tabindex="3">
                                            <option value=0>----Select Service Group----</option>
                                            <%
                                                        try
                                                        {
                                                          results=statement.executeQuery("select * from HRM_MST_SERVICE_GROUP"); 
                                                          while(results.next()) 
                                                          {
                                                              out.print("<option value='" + results.getInt("SERVICE_GROUP_ID") + "'>" + results.getString("SERVICE_GROUP_NAME") + "</option>");                      
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
                                            <td>Post Rank<label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
                                            <td>
                                             <select name="cmbPostRank" id="cmbPostRank" tabindex="4">
                                             <option value=0>--Select PostRank--</option>
                                             </select>
                                            </td>
                                        </tr> 
                                         <tr>
                                            <td>Post Category 
                                            <label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
                                            <td>
                                              <select name="cmbPostCategory" id="cmbPostCategory" tabindex="5">
                                            <option value=0>---Select PostCategory---</option>
                                            <%
                                                        try
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
                                                        }  
                                        %>
                                            
                                        </select>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td>No of Sanctioned Posts<label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
                                            <td>
                                                <input type=text name="txtNoPost" id="txtNoPost" maxlength="4" tabindex="6" onkeypress="return  numbersonly1(event,this)" />
                                            </td>
                                        </tr>
                                       
                                        <tr>
                                      <td class="td">
                                        Remarks
                                      </td>
                                      <td>
                                        <textarea name="Remarks" cols="25" rows="5" ></textarea>
                                      </td>
                                  </tr>
                                  <tr>
                                  <td colspan=2 class="tdH">
                                    <div>
                                        <table>
                                            <tr>
                                            <td>
                                       <input type="Button" value="  Save " id="Add" onclick="callServer('Add','null')" name="cmdAdd" tabindex="8" style="display:block"> 
                                       </td><td>
                                       <input type="Button" value=" Update" onclick="callServer('Update','null')"  name="cmdUpdate" style="display:none">
                                       </td><td>
                                       <input type="Button" value=" Delete" id="Revoke" onclick="callServer('Delete','null')"  name="cmdDelete" style="display:none">
                                       </td><td>
                                       <input type="Button" value="Clear All" onclick="clearAll()" name="cmdClearAll" style="display:block">                                 
                                       </td><td>
                                       </tr>
                                       </table>
                                    </div>
                                  </td>                              
                                </tr> 
                                <tr>
                                  <td colspan=2>&nbsp;</td>
                                  </tr>
                                   </table>
                                   <table name="Existing" id="Existing"  border="1" width="100%"  style="font-family:arial;">
                                    <tr>
                                            <td colspan="11">
                                                <b>Existing  Details of Posts associated with this Template
</b>
                                            </td>
                                    </tr>
                                    
                                    <tr>
                                            <th rowspan="2">View</th>
                                            <th rowspan="2">Service Group</th>
                                            <th rowspan="2">Post Rank</th>
                                            <th rowspan="2">Post Category</th>
                                            <th rowspan="2">No Of Sanctioned Posts</th>
                                            <th rowspan="2">Remarks</th>
                                            
                                    </tr>
                                            
                                            <tr>
                                <tbody id="tblList" name="tblList">
                                    </tbody>
                            </tr>
                            </table>
                            </div>
                            </td>
                            </tr>
                            </table>
                             
                         </div>
                       </div>
                       
                        <div class="tab-page">
                        <h2 class="tab">Diversion Details</h2>
                        <div align="center">
                            <table width="100%">
                             <tr>
                                   <td colspan="2">
                                   <label>Details of Posts Diverted to Other Offices</label>
                                   <div id="Diversion" >
                                        <table  cellspacing="1" cellpadding="3" width="100%" style="border-color:rgb(185,201,202); border-width:medium; border-style:groove;">    
                                       
                                        <tr>
                                            <td>Service Grouping<label style="color:rgb(255,0,0);"> &nbsp;*</label> </td>
                                            <td>
                                              <select name="cmbServiceGroupTo" id="cmbServiceGroupTo" onchange="callServer1('ServiceGroup',null)" tabindex="3">
                                            <option value=0>----Select Service Group----</option>
                                            <%
                                                        try
                                                        {
                                                          results=statement.executeQuery("select * from HRM_MST_SERVICE_GROUP"); 
                                                          while(results.next()) 
                                                          {
                                                              out.print("<option value='" + results.getInt("SERVICE_GROUP_ID") + "'>" + results.getString("SERVICE_GROUP_NAME") + "</option>");                      
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
                                            <td>Post Rank<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                            <td>
                                              <select name="cmbPostRankTo" id="cmbPostRankTo" tabindex="4">
                                             <option value=0>--Select PostRank--</option>
                                             </select>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td>Post Category<label style="color:rgb(255,0,0);"> &nbsp;*</label> </td>
                                            <td>
                                              <select name="cmbPostCategoryTo" id="cmbPostCategoryTo" tabindex="5">
                                            <option value=0>---Select PostCategory---</option>
                                            <%
                                                        try
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
                                                        }  
                                        %>
                                            
                                        </select>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td>No of Posts Diverted to Other<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                            <td>
                                              <input type="text" name="txtNoOfPostTo" maxlength="4" size="30" onkeypress="return  numbersonly1(event,this)"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td>Details of Diversion To Others</td>
                                            <td>
                                            <textarea name="RemarksTo" cols="20" rows="5" ></textarea>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>No of Posts Diverted to From<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                            <td>
                                              <input type="text" name="txtNoOfPostFrom" maxlength="4" size="30" onkeypress="return  numbersonly1(event,this)" onblur="noofpostcheck()"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td>Details of Diversion From Others</td>
                                            <td>
                                            <textarea name="RemarksFrom" cols="20" rows="5" ></textarea>
                                            </td>
                                        </tr>
                                        </table>
                                        
                                        </div>
                                        </td>
                                        </tr>
                                        <tr>
                                  <td colspan=2 class="tdH">
                                    <div>
                                        <table>
                                            <tr>
                                                <td>
                                       <input type="Button" value="  Save " id="Add" onclick="callServer1('Add','null')" name="cmdAddTo" tabindex="8" style="display:block"> 
                                       </td><td>
                                       <input type="Button" value=" Update" onclick="callServer1('Update','null')"  name="cmdUpdateTo" style="display:none">
                                       </td><td>
                                       <input type="Button" value=" Delete" id="Revoke" onclick="callServer1('Delete','null')"  name="cmdDeleteTo" style="display:none">
                                       </td><td>
                                       <input type="Button" value="Clear All" onclick="clearAllTo()" name="cmdClearAllTo" style="display:block">                                 
                                       </td><td>
                                        </tr>
                                        </table>
                                        </div>
                                  </td>                              
                                </tr> 
                                <tr>
                                  <td colspan=2>&nbsp;</td>
                                  </tr>
                                  </table>
                                        
                                   
                                   <table name="ExistingTo" id="ExistingTo"  border="1" width="100%"  style="font-family:arial;">
                                    <tr>
                                            <td colspan="10">
                                                <b>Existing  Details
</b>
                                            </td>
                                    </tr>
                                    
                                    <tr>
                                            <th rowspan="2">View</th>
                                            
                                            <th rowspan="2">Service Group</th>
                                            <th rowspan="2">Post Rank</th>
                                            <th rowspan="2">Post Category</th>
                                            <th rowspan="2">No of Posts Diverted To</th>
                                            <th rowspan="2">Remarks To</th>
                                            <th rowspan="2">No of Posts Diverted From</th>
                                            <th rowspan="2">Remarks From</th>
                                            
                                            
                                    </tr>
                                            
                                            <tr>
                                <tbody id="testTo" name="testTo">
                                    </tbody>
                            </tr>
                            </table>
                          </div>
                   </div>
                   
           </div>-->
            
                   
                <br>
                <%
                statement.close();
                connection.close();
                %>
                <div id="bghead" class="tdH">
                    <center>  
                        <!--<input type="button" value="Ok " name="cmbSubmit" onclick="return nullValue();"> &nbsp;-->
                        <input type="RESET" value="Clear All" name="cmdClear" onclick="clear1()">&nbsp; 
                        <input type="BUTTON" value="Exit" onclick="closeWindow();"> &nbsp;
                    </center>
                </div>                         
                
        </form>
  </body>
</html>

