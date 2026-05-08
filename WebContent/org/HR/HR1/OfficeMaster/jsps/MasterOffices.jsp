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
      <title>Create New Office</title>    
          <script type="text/javascript" src="../scripts/OfficeValidation.js" >    
          </script>
          <script type="text/javascript">
          function closeWindow()
          {              
              self.close();
              window.opener.focus();
          }
          </script>
          <!--<link href="css/sample2.css" rel="stylesheet" media="screen"/>-->
          <!--<link href="../../../../../css/green.css" rel="stylesheet" media="screen"/>-->
          <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    </head>
 <body> 
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
   // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

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
        <form name="frmOffice" method="POST" action="../../../../../ServletOffices.con" onsubmit="return nullcheck()" class="table">
                <style>
                .divClass{display: none;  }                
                .bgClass{color: black ; width: 100%; border-bottom: 1px solid Black ; padding: 0px; margin: 0px;background-color: rgb(207,222,225);}
                .bghead{color: black ; width: 100%; border-bottom: 1px solid Black ; padding: 0px; margin: 0px;background-color: rgb(207,222,225);}
                </style>
                <table  cellspacing="1" cellpadding="3" width="100%" class="bodytext">
                    <tr>
                        <td>
                            <center>
                            <div id="bghead" width="100%" class="bgClass">
                            <h2>Offices Master</h2>
                            </center>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>                        
                              <table  cellspacing="3" cellpadding="1"  width="100%">
                                  <TR>
                                      <TD vAlign=top width=209 height=3>
                                          <P style="MARGIN-BOTTOM: -16px"><B><FONT face=Tahoma color=#808080 
                                                                        size=2>Office Id</FONT></B></P>
                                      </TD>
                                      <TD vAlign=top width=701 height=3>
                                          (System Will Generate Automatically)
                                          <input type="HIDDEN" name="mode" value="Insertion">
                                      </TD>
                                  </TR>
                                  <tr>
                                      <td class="td">Office Name <label style="color:rgb(255,0,0);">*</label> </td>
                                      <td style="color:rgb(255,51,102);">
                                        <input type="text" name="txtOffName" size="25" maxlength="75"/>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">Office Short Name
                                      <label style="color:rgb(255,0,0);">*</label></td>
                                      <td>
                                        <input type="text" name="txtShortName" maxlength="30" size="15" />
                                      </td>
                                  </tr>  
                                   <tr>
                                      <td class="td">Office Head Code
                                      <label style="color:rgb(255,0,0);">*</label></td>
                                      <td>
                                        <select name="cmbHeadCode">                                        
                                            <option value>----Select Cadre----</option>
                                            <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select * from HR_MST_CADRE"); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getInt("Cadre_Id") + "'>" + results.getString("Cadre_Name") + "</option>");                      
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
                                      <td colspan="2">
                                         <div id="Taddresses" class="bgClass" onclick="ShowOrHideDiv('addresses')" ><label>Address</label></div>
                                         <div id="addresses"  >
                                         <table cellspacing="1" cellpadding="3" width="100%" style="border-color:rgb(185,201,202); border-width:medium; border-style:groove;">
                                         <tr>
                                            <td class="td" width="23%">Office Address</td>
                                            <td width="77%">
                                              <P> &nbsp;&nbsp;1. 
                                              
                                              <label style="color:rgb(255,0,0);">*</label>&nbsp;<input type="text" name="txtAdd1" maxlength="50" size="25"/>
                                              </P>
                                              <P>&nbsp;&nbsp;2.&nbsp;&nbsp;&nbsp;
                                              <input type="text" name="txtAdd2" maxlength="50" size="25"/></P>
                                              <P>&nbsp;&nbsp;3.&nbsp;&nbsp;&nbsp;
                                              <input type="text" name="txtAdd3" maxlength="50" size="25"/>&nbsp;&nbsp;&nbsp;</P>
                                            </td>
                                        </tr> 
                                         <tr>
                                            <td class="td" width="23%">Pincode
                                            <label style="color:rgb(255,0,0);">*</label></td>
                                            <td width="77%">
                                              <input type="text" name="txtPinCode" maxlength="6" size="6" onkeyup="isInteger(this,event)"/>
                                            </td>
                                        </tr> 
                                         <tr>
                                            <td class="td" width="23%">District
                                            <label style="color:rgb(255,0,0);">*</label></td>
                                            <td width="77%">
                                              <SELECT size=1 name=cmbDistrict>
                                                  <option value>----Select District----</option>
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
                                                        {}      
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
                                            <td class="td" width="23%">STD Code<label style="color:rgb(255,0,0);">*</label></td>
                                            <td width="77%">
                                              <input type="text" name="txtSTDCode" maxlength="6" size="6" onkeyup="isInteger(this,event)"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="23%">Phone Number <label style="color:rgb(255,0,0);">*</label></td>
                                            <td width="77%">
                                              <input type="text" name="txtPhoneNo" maxlength="10" size="10" onkeyup="isInteger(this,event)"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="23%">Additional Phone Numbers(seperated&nbsp;by&nbsp;/&nbsp;)                                      <label style="color:rgb(255,0,0);"></label></td>
                                            <td width="77%">
                                              <input type="text" name="txtAddPhoneNo" maxlength="60" size="30" />
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="23%">FAX Number
                                            <label style="color:rgb(255,0,0);">*</label></td>
                                            <td width="77%">
                                              <input type="text" name="txtFAXNo" maxlength="10" size="10" onkeyup="isInteger(this,event)"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="23%">Additional FAX Numbers( seperated&nbsp;by&nbsp;/&nbsp;)                                      <label style="color:rgb(255,0,0);"></label></td>
                                            <td width="77%">
                                              <input type="text" name="txtAddFAXNo" maxlength="60" size="30" />
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="23%">Office&nbsp;E-Mail&nbsp;ID
                                            <label style="color:rgb(255,0,0);">*</label></td>
                                            <td width="77%">
                                              <input type="text" name="txtEMail" maxlength="75" size="25" onblur="echeck(this.value)"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td class="td" width="23%">Additional E-Mail&nbsp;ID
                                            ( seperated&nbsp;by&nbsp;/&nbsp;)<label style="color:rgb(255,0,0);"></label></td>
                                            <td width="77%">
                                              <textarea name="txtAddEMail" cols="25" rows="4"></textarea>
                                            </td>
                                        </tr>
                                        </table>
                                  </div>
                                  </td>
                                  </tr>
                                  <tr>
                                      <td class="td">Office&nbsp;Level
                                      <label style="color:rgb(255,0,0);">*</label></td>
                                      <td>
                                        <SELECT size=1 name=cmbLevelId onchange="checkLevel()">   
                                            <option value>----Select OfficeLevel----</option>
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
                                      <td class="td">Controlling Office
                                      <label style="color:rgb(255,0,0);">*</label></td>
                                      <td>
                                        <input type="text" name="txtContrllingOfficeID" maxlength="70" size="20" disabled/> 
                                        <input type="HIDDEN" name="txtHCode" />
                                        <input type="Button" value="Select An Office" onclick="popupWindow()" name="cmdSelectOffice" disabled/>
                                      </td>
                                  </tr>                                  
                                  <tr>
                                      <td class="td">Office&nbsp;Old Code                                      <label style="color:rgb(255,0,0);"></label></td>
                                      <td>
                                        <input type="text" name="txtOCode" maxlength="6" size="6" />
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">Primary Work ID 
                                      <label style="color:rgb(255,0,0);">*</label><label style="color:rgb(255,0,0);"></label></td>
                                      <td>                                        
                                        <select name="cmbPrimaryID">                                        
                                            <option value>----Select Work Nature----</option>
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
                                      <td class="td">Secondary Work ID  
                                      <label style="color:rgb(255,0,0);">*</label>
                                      <label style="color:rgb(255,0,0);"/></td>
                                      <td>
                                        <select name="cmbSecondaryID">                                        
                                            <option value>----Select Work Nature----</option>
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
                                                  {
                                                  System.out.println("exep : " + e);
                                                  }      
                                            %>                 
                                        </select>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">Date Of Formation 
                                      <label style="color:rgb(255,0,0);">*</label>     <label style="color:rgb(255,0,0);"></label></td>
                                      <td>
                                        <input type="text" name="txtDOF" maxlength="10" size="10" onblur="validate_date('frmOffice','txtDOF')"/>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">HRA CCA Class<label style="color:rgb(255,0,0);"> </label>
                                      <label style="color:rgb(255,0,0);">*</label></td>
                                      <td>
                                        <select name="cmbClassID">
                                            <option value>----Select Class----</option>
                                            <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select * from HR_MST_HRA_CCA_CLASSES"); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getString("HRA_CCA_Class_Id") + "'>" + results.getString("HRA_CCA_Class_Desc") + "</option>");                      
                                                    }
                                                    results.close();
                                                    statement.close();
                                                    connection.close();
                                                  }
                                                  catch(Exception e)
                                                  {}      
                                            %>
                                        </select>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">Is Accounting Unit</td>
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
                                  <tr>
                                      <td colspan=3 class="td">
                                      <div id="bghead" width="100%" class="bgClass">
                                        <center>  
                                          <input type="SUBMIT" value="  Submit  " name="cmdSub"> &nbsp;
                                          <input type="RESET" value=" Clear All " name="cmdClear">&nbsp; 
                                          <input type="BUTTON" value=" Cancel " name="cmdCancel" onclick="closeWindow();"> &nbsp;                                                              
                                        </center>
                                      </div>
                                      </td>                              
                                  </tr>                              
                              </table>                              
                        </td>                        
                    </tr>
              </table>
        </form>
  </body>
</html>

