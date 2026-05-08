
<!--
    File Name     : EditNewStaffStrengthTemp.jsp
    Purpose       : To create form that allows us add,modify and delete records residing in database
    //References    : EditStaffStrength.js,EditStaffStrengthValidations.js,yellow.css
    //Servlet Ref.  : EditStaffStrengthServlet.java,EditStaffStrengthServlet1.java
-->

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <title>Edit Staff Strength Template</title>
    
          <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
          
          <script type="text/javascript" src="../scripts/EditStaffStrengthAjax.js"></script>
          <script type="text/javascript" src="../scripts/EditStaffStrengthValidation.js"></script>
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
 <form name="frmStaffStrength" method="Post" action="../../../../../EditStaffStrengthServlet1.con">
        <div id="dhtmltooltip"></div>
    <script type="text/javascript" src="test.js"></script>
               
                <table  cellspacing="1" cellpadding="3"  width="100%" border="1" class="bgbody">
                    <tr>
                        <td class="tdH">
                            <center><h3>Edit Staff Strength Template</h3></center>
                        </td>
                    </tr>
                    <tr>
                        <td>                        
                              <table  cellspacing="3" cellpadding="1"  width="100%" >
                                  <tr>
                                      <td>Office Level To Which The Template is Applicable</td>
                                      
                                      <td>
                                      <select name="cmbOfficeLevel" id="cmbOfficeLevel" onchange="callServer('OfficeLevel',null)">
                                      <option value=0>--Select OfficeLevel--</option>
                                      <%
                                                        try
                                                        {
                                                          results=statement.executeQuery("select * from COM_MST_OFFICE_LEVELS"); 
                                                          while(results.next()) 
                                                          {
                                                              out.print("<option value='" + results.getString("OFFICE_LEVEL_ID") + "'>" + results.getString("OFFICE_LEVEL_NAME") + "</option>");                      
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
                                      <td>Sanction Strength Template Id</td>
                                      <td>
                                            <select name="cmbSSTemp_Id" id="cmbSSTemp_Id" onchange="callServer('TempId',null)" onfocus="staffCheck()">
                                            <option value=0>--Select TemplateId--</option>
                                            </select>
                                          <!--<input type=text name="txtSSTemp_Id" disabled>
                                          <input type=hidden name="txtSSTemp_Id1">-->
                                      </td>
                                  </tr>
                                  
                                  <tr>
                                      <td>Template Name</td>
                                      <td>
                                      <input type="text" name="txtTemplate_Name" id="txtTemplate_Name" size="35" tabindex="1" disabled/>
                                      <input type=hidden name="txtTemplate_Name1" id="txtTemplate_Name1"> 
                                      </td>
                                  </tr>
                                  
                                  
                                  <tr>
                                    <td colspan=2 class="tdH">
                                        <b>Details of Posts to be included in this template</b>
                                    </td>
                                    <!--<tr>
                                      <td>
                                        Sl No
                                      </td>
                                      <td>
                                        <input type=text name="txtSl_No" id="txtSl_No" value="1" disabled>
                                        <input type=hidden name="txtSl_No1" id="txtSl_No1" >
                                      </td>
                                  </tr>-->
                                   
                                   <tr>
                                      <td>
                                        Service Grouping<label style="color:rgb(255,0,0);">*</label>
                                      </td>
                                      <td>
                                        <select name="cmbServiceGroup" id="cmbServiceGroup" onchange="callServer('ServiceGroup',null)" tabindex="3" onfocus="staffCheck()">
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
                                      <td>
                                       Name of the Post/Cadre
                                    <label style="color:rgb(255,0,0);">*</label>
                                      </td>
                                      <td>
                                        <select name="cmbPostRank" id="cmbPostRank" tabindex="4" onfocus="staffCheck()">
                                      <option value=0>--Select PostRank--</option>
                                      

                                      </select>
                                      </td>
                                  </tr>
                                  <!--<tr>
                                      <td>
                                        Post Category<label style="color:rgb(255,0,0);">*</label>
                                      </td>
                                      <td>
                                        <select name="cmbPostCategory" id="cmbPostCategory" tabindex="5" onfocus="staffCheck()">
                                            <option value=0>---Select PostCategory---</option>
                                            <%
                                                       /* try
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
                                                        }*/  
                                        %>
                                            
                                        </select>
                                      </td>
                                  </tr>-->
                                   <tr>
                                      <td>
                                        No of Posts<label style="color:rgb(255,0,0);">*</label>
                                      </td>
                                      <td>
                                        <input type=text name="txtNoPost" id="txtNoPost" maxlength="4" tabindex="6" onkeypress="return  numbersonly1(event,this)" onfocus="staffCheck()" onchange="return zerocheck()"/>
                                        
                                      </td>
                                  </tr> 
                                  
                                  <tr>
                                      <td>
                                        Remarks
                                      </td>
                                      <td>
                                        <textarea rows="4" name="Remarks" cols="38" id="Remarks" tabindex="7" onfocus="staffCheck()"></textarea>
                                      </td>
                                  </tr>
                                  
                                  <tr>
                                      <td colspan=2 class="tdH">
                                      <div>
                                        <table border="0">
                                            <tr>
                                                <td>
                                                  <input type="Button" value="  Add " id="Add" onclick="callServer('Add','null')" name="cmdAdd" tabindex="8" style="display:block"> 
                                                 </td><td>                                                  
                                                  <input type="Button" value=" Update" onclick="callServer('Update','null')"  name="cmdUpdate" style="display:none">
                                                  </td><td>
                                                  <input type="Button" value=" Delete" id="Revoke" onclick="callServer('Delete','null')"  name="cmdDelete" style="display:none">
                                                  </td><td>
                                                  <input type="Button" value="Clear All" onclick="clearAll()" name="cmdClearAll" style="display:block">
                                                  </td>
                                                
                                                </tr>
                                            </table>
                                      </div>
                                      </td>                              
                                  </tr>      
                                  <tr>
                                  <td colspan=2>&nbsp;</td>
                                  </tr>
                                  </table>
                                  
                                   <!--<div id="mydiv" name="mydiv"> -->
                                    <table name="Existing" id="Existing"  border="1" width="100%"  style="font-family:arial;">
                                    <tr>
                                            <td colspan="10">
                                                <b>Existing  Details of Posts associated with this Template
</b>
                                            </td>
                                    </tr>
                                    
                                    <tr>
                                            <td class="tdH"  >View</td>
                                            <td class="tdH">Service Group</td>
                                            <td class="tdH">Post Rank</td>
                                            <!--<th>Post Category</th>-->
                                            <td class="tdH">No Of Posts</td>
                                            <td class="tdH">Remarks</td>
                                            
                                    </tr>
                            <tr>
                                <tbody id="tblList" name="tblList">
                                    </tbody>
                            </tr>
                                    <tr>
                                        <td colspan="10" class="tdH"><center>
                                            <input type=Submit value=Submit onclick="return nullGrid()"/>
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

