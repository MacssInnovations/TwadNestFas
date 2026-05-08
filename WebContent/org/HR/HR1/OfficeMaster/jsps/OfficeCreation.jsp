<!--
    File Name     : MasterBenefit.jsp
    Purpose       : To create form that allows us add,modify and delete records residing in database
    References    : BenefitAjax.js,BenefitValidations.js,sample2.css
    Servlet Ref.  : ServletBenefitMaster.java
-->

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <meta http-equiv="cache-control" content="no-cache">
      <title>Create New Office</title>    
          <script type="text/javascript" src="../scripts/OfficeValidation.js" >    
          </script>
          <script type="text/javascript" src="../../../../Security/scripts/tabpane.js">
          </script>
          <script type="text/javascript"
                  src="../../../../Library/scripts/controllingOffice.js">
          </script>
          
          <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
          </script> 
            
          <link href='../../../../../css/yellowTab.css' rel='stylesheet' media='screen'/>
          <link href='../../../../../css/sample3.css' rel='stylesheet' media='screen'/>
          <style type="text/css">
                .divClass{display: none;  }                                
          </style>
    </head>
 <body class="bgbody"> 
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
                        <td class="bgClass">
                            <center>                           
                            <h3>New Office Creation</h3>                            
                            </center>
                        </td>
                    </tr>                    
              </table>
        <form name="frmOffice" method="POST" action="../../../../../ServletOffices.con" onsubmit="return nullcheck()" >                
                <div class="tab-pane" id="tab-pane-1">
                    <div class="tab-page">
                        <h2 class="tab">Office Details</h2>
                        <div align="center">
                            <table  cellspacing="3" cellpadding="1"  width="100%">
                                  <TR>
                                      <TD vAlign=top >
                                          <P style="MARGIN-BOTTOM: -16px"><B><FONT face=Tahoma color=#808080 
                                                                        size=2>Office Id</FONT></B></P>
                                      </TD>
                                      <TD vAlign=top >
                                          (System Generated)                                          
                                      </TD>
                                  </TR>
                                  <tr>
                                      <td class="td">Office Name <label style="color:rgb(255,0,0);">&nbsp;*</label> </td>
                                      <td style="color:rgb(255,51,102);">
                                        <input type="text" name="txtOffName" size="25"
                                               maxlength="60"/>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">Office Short Name 
                                    <label style="color:rgb(255,0,0);">
                                        &nbsp;*
                                    </label>
                                    </td>
                                      <td>
                                        <input type="text" name="txtShortName" maxlength="30" size="15" />
                                      </td>
                                  </tr> 
                                  
                                  <tr>
                                      <td class="td">Office&nbsp;Level 
                                      <label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
                                      <td>
                                        <SELECT size=1 name=cmbLevelId  onchange="checkLevel();">   
                                            <option value="0">
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
                                  </tr>
                                  
                                  <tr>
                                      <td class="td">Office Head By 
                                      <label style="color:rgb(255,0,0);">  &nbsp;*</label></td>
                                      <td>
                                        <select name="cmbHeadCode">                                        
                                            <option value>
                                                                  ----Select
                                                                  Cadre----
                                                            </option> 
                                            <%
                                                 try
                                                  {
                                                    results=statement.executeQuery("select * from hrm_mst_cadre order by Cadre_Name"); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getInt("Cadre_Id") + "'>" + results.getString("Cadre_Name") + "</option>");                      
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
                                  </tr>
                                  
                                  
                                  <tr>
                                      <td class="td">Controlling Office Id  
                                      <label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
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
                                                <input type="text" name="txtContrllingOfficeID" maxlength="70" size="6"
                                                       onchange="SaveToHidden();"
                                                       onkeyup="isInteger(this,event);"/>                                                 
                                                <input type="hidden" name="txtHContrllingOfficeID" />
                                            </td>
                                            <td>
                                                <SELECT size=1 name=cmbControllingLevel onchange="getOfficesByLevel()">   
                                                <option value="0">
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
                                                                                    ----Select
                                                                                    Office
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
                                                <select name="cmbSelectOffice" id="cmbSelectOffice" style="visibility:hidden" onchange="selectControllineOffice()">
                                                    <option value=0>
                                                                                    ----Select
                                                                                    Office----
                                                                              </option>                                               
                                                </select>
                                            </td>
                                        </tr>
                                        </table>
                                      </td>
                                  </tr>                                                                 
                                                                                                      
                                  <tr>
                                      <td class="td">Office&nbsp;Old Code                                      <label style="color:rgb(255,0,0);"></label></td>
                                      <td>
                                        <input type="text" name="txtOCode"
                                               maxlength="3" size="3"
                                               onkeyup="isInteger(this,event);"/>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">Primary Work&nbsp;Nature  
                                      <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                      <td>                                        
                                        <select name="cmbPrimaryID">                                        
                                            <option value="0">
                                                                  ----Select
                                                                  Work Nature----
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
                                      <td class="td">Date Of Formation</td>
                                      <td>
                                        <input type="text" name="txtDOF" maxlength="10" size="10" onblur="validate_date('frmOffice','txtDOF')"/>
                                    in the Format DD/MM/YYYY
                                </td>
                                  </tr>
                                  <tr>
                                      <td class="td">HRA Class</td>
                                      <td>
                                        <select name="cmbHRAClassID">
                                            <option value=0>
                                                                  ----Select
                                                                  Class----
                                                            </option>                                        
                                            <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select * from HR_MST_HRA_CCA_CLASSES"); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getString("HRA_CCA_Class_Id") + "'>" + results.getString("HRA_CCA_Class_Desc") + "</option>");                      
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
                                      <td class="td">CCA Class </td>
                                      <td>
                                        <select name="cmbCCAClassID">
                                            <option value=0>
                                                                  ----Select
                                                                  Class----
                                                            </option>                                            
                                            <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select * from HR_MST_HRA_CCA_CLASSES"); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getString("HRA_CCA_Class_Id") + "'>" + results.getString("HRA_CCA_Class_Desc") + "</option>");                      
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
                                      <td class="td">Is Accounting Unit 
                                    <label style="color:rgb(255,0,0);">
                                        &nbsp;*
                                    </label>
                                </td>
                                      <td>
                                        <input type="radio" name="optIAU" checked value="yes"/>Yes<input type="radio" name="optIAU" value="no"/>No</td>
                                  </tr>
                                  <tr>
                                      <td class="td">Wings Applicable</td>
                                      <td>
                                        <input type="radio" name="optWA" checked value="yes"/>Yes
                                        <input type="radio" name="optWA" value="no"/>No
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">
                                        Remarks
                                      </td>
                                      <td>
                                        <textarea name="txtRemarks" cols="25" rows="5" ></textarea>
                                      </td>
                                  </tr>                                                                
                              </table>
                        </div>
                    </div>
                    <div class="tab-page">
                        <h2 class="tab">Contact Details</h2>
                        <div align="center">
                            <table width="100%">
                            <tr>
                                      <td colspan="2">
                                         <div id="Taddresses" class="bgClass" onclick="ShowOrHideDiv('addresses')" ><label>Office Address</label></div>
                                         <div id="addresses"  >
                                         <table cellspacing="1" cellpadding="3" width="100%" style="border-color:rgb(185,201,202); border-width:medium; border-style:groove;">
                                         
                                         <tr>
                                            <td width="43%">
                                              Address&nbsp;1.
                                            </td>
                                            <td width="57%">
                                            <input type="text" name="txtAdd1" maxlength="50" size="25"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="43%">
                                              Address&nbsp;2.
                                            </td>
                                            <td width="57%">
                                              <input type="text" name="txtAdd2" maxlength="50" size="25"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="43%">
                                             City / Town<label style="color:rgb(255,0,0);"> &nbsp;*</label>
                                            </td>
                                            <td width="57%">
                                              <input type="text" name="txtAdd3" maxlength="50" size="25"/>
                                            </td>
                                        </tr> 
                                         <tr>
                                            <td class="td" width="43%">Pincode</td>
                                            <td width="57%">
                                              <input type="text" name="txtPinCode" maxlength="6" size="6"
                                                     onkeyup="isInteger(this,event);"/>
                                            </td>
                                        </tr> 
                                         <tr>
                                            <td class="td" width="43%">District 
                                            <label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
                                            <td width="57%">
                                              <SELECT size=1 name=cmbDistrict>
                                                  <option value="0">
                                                                                          ----Select
                                                                                          District----
                                                                                    </option>
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
                                                            System.out.println("exception occured : " + e);
                                                        }      
                                                  %>
                                                </SELECT>
                                            </td>
                                        </tr> 
                                        </table>
                                        </div>
                                        </td>
                                   </tr>
                                   <tr>
                                   <td colspan="2">
                                   <div id="Tcontacts" class="bgClass" onclick="ShowOrHideDiv('contacts')"><label>Contacts</label></div>
                                   <div id="contacts" >
                                        <table  cellspacing="1" cellpadding="3" width="100%" style="border-color:rgb(185,201,202); border-width:medium; border-style:groove;">    
                                        <tr>    
                                            <td class="td" width="43%">STD Code </td>
                                            <td width="57%">
                                              <input type="text" name="txtSTDCode" maxlength="6" size="6" onkeyup="isInteger(this,event)"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="43%">Phone Number </td>
                                            <td width="57%">
                                              <input type="text" name="txtPhoneNo" maxlength="10" size="10" onkeyup="isInteger(this,event)"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="43%">Additional Phone Numbers(seperated&nbsp;by&nbsp;/&nbsp;)                                      <label style="color:rgb(255,0,0);"></label></td>
                                            <td width="57%">
                                              <input type="text" name="txtAddPhoneNo"
                                                     maxlength="50" size="30" />
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="43%">FAX Number </td>
                                            <td width="57%">
                                              <input type="text" name="txtFAXNo" maxlength="10" size="10" onkeyup="isInteger(this,event)"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="43%">Additional FAX Numbers( seperated&nbsp;by&nbsp;/&nbsp;)                                      <label style="color:rgb(255,0,0);"></label></td>
                                            <td width="57%">
                                              <input type="text" name="txtAddFAXNo"
                                                     maxlength="50" size="30" />
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="43%">Office&nbsp;E-Mail&nbsp;ID</td>
                                            <td width="57%">
                                              <input type="text" name="txtEMail" maxlength="75" size="25" onblur="echeck(this.value)"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="43%">Additional E-Mail&nbsp;ID
                                            ( seperated&nbsp;by&nbsp;/&nbsp;)<label style="color:rgb(255,0,0);"></label></td>
                                            <td width="57%">
                                              <textarea name="txtAddEMail" cols="25" rows="4"></textarea>
                                            </td>
                                        </tr>
                                        </table>
                                  </div>
                                  </td>
                                  </tr>
                                </table>
                        </div>
                    </div>
                </div>
                <br>
                <%
                statement.close();
                connection.close();
                %>
                <div id="bghead" class="bgClass">
                    <center>  
                        <input type="SUBMIT" value="  Submit  " name="cmdSub"> &nbsp;
                        <input type="RESET" value=" Clear All " name="cmdClear">&nbsp; 
                        <input type="BUTTON" value=" Cancel " name="cmdCancel" onclick="closeWindow();"> &nbsp;                                                              
                    </center>
                </div>                         
                
        </form>
  </body>
</html>

