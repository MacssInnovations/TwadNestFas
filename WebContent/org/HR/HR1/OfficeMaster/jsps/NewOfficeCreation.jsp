<!--
    File Name     : MasterBenefit.jsp
    Purpose       : To create form that allows us add,modify and delete records residing in database
    References    : BenefitAjax.js,BenefitValidations.js,sample2.css
    Servlet Ref.  : ServletBenefitMaster.java
-->

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
  
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <meta http-equiv="cache-control" content="no-cache">
      <title>Create New Office</title>    
          <script type="text/javascript" src="../scripts/NewOfficeValidation.js"></script>
          <script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
          <script type="text/javascript"  src="../scripts/controllingOffice1.js"></script>
          <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
          
    
          
          <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
          </script> 
             <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
          <link href='../../../../../css/RWS_CSSColour.css' rel='stylesheet' media='screen'/>
          <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
          <script type="text/javascript"       src="../scripts/CalendarControl.js"></script>
          <style type="text/css">
                .divClass{display: none;  }                                
          </style>
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
        <table  cellspacing="1" cellpadding="3" width="100%" class="table">
                    <tr>
                        <td class="tdH">
                            <center>                           
                            <h3>New Office Creation</h3>                            
                            </center>
                        </td>
                    </tr>                    
              </table>
        <form name="frmOffice" method="POST" action="../../../../../NewServletOffices.con" onsubmit="return nullcheck()" >                
                <div class="tab-pane" id="tab-pane-1" >
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
                                      <td>Office Name <label style="color:rgb(255,0,0);">&nbsp;*</label> </td>
                                      <td style="color:rgb(255,51,102);">
                                        <input type="text" name="txtOffName"
                                               size="40"
                                               maxlength="60"/>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td>Office Short Name 
                                    <label style="color:rgb(255,0,0);">
                                        &nbsp;*
                                    </label>
                                    </td>
                                      <td>
                                        <input type="text" name="txtShortName" maxlength="55"
                                               size="40" onfocus="return officeCheck()"/>
                                      </td>
                                  </tr> 
                                  
                                  <tr>
                                      <td>Office&nbsp;Level 
                                      <label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
                                      <td>
                                        <SELECT size=1 name=cmbLevelId  onchange="checkLevel();setDefaultCadre(this.value);setOfficeLevel()" onfocus="return officeCheck()">   
                                            <option value="0">
                                                                  ----Select OfficeLevel------------
                                                            </option>
                                            <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select office_level_id,office_level_name from COM_MST_OFFICE_LEVELS where office_level_id not in 'HO'"); 
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
                                      <td>Office Headed By 
                                      <label style="color:rgb(255,0,0);">  &nbsp;*</label></td>
                                      <td>
                                        <select name="cmbHeadCode" id="cmbHeadCode" onfocus="return officeCheck()">                                        
                                            <option value=0>
                                                                  ----Select Cadre------------------
                                                            </option> 
                                            <%
                                                 /*try
                                                  {
                                                    results=statement.executeQuery("select b.cadre_id,b.cadre_name from com_mst_office_levels a,hrm_mst_cadre b where a.office_head_cadre_id=b.cadre_id order by b.cadre_name"); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getInt("Cadre_Id") + "'>" + results.getString("Cadre_Name") + "</option>");                      
                                                    }
                                                    results.close();
                                                  }
                                                  catch(Exception e)
                                                  {
                                                  System.out.println("exception occured : " + e);
                                                  }*/
                                            %>                 
                                        </select>
                                      </td>
                                  </tr>
                                  
                                  
                                  <tr>
                                      <td>Controlling Office Id  
                                      <label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
                                      <td>
                                        <table>
                                        <tr>                                       
                                            <td>&nbsp;</td>
                                            <td>Office Level</td>
                                            <td><div id="divType1" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType2" style="visibility:hidden">Select Office</div></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <input type="text" name="txtContrllingOfficeID" maxlength="4" size="20" onkeypress="return  numbersonly1(event,this)" onchange="checkAttachedoffice();officename()" onfocus="return officeCheck()"/>                                                 
                                                <input type="hidden" name="txtHContrllingOfficeID" />
                                            </td>
                                            <td>
                                                <SELECT size=1 name=cmbControllingLevel id="cmbControllingLevel" onchange="getOfficesByLevel()" onfocus="return officeCheck()">   
                                                <option value="0">
                                                                                    ----Select
                                                                                    OfficeLevel----
                                                                              </option>
                                                <%
                                                     /* try
                                                      {
                                                        results=statement.executeQuery("select office_level_id,office_level_name from COM_MST_OFFICE_LEVELS"); 
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
                                                <select name="cmbOfficeType" style="visibility:hidden" onchange="getOfficesByType()" onfocus="return officeCheck()">
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
                                                <select name="cmbSelectOffice" id="cmbSelectOffice" style="visibility:hidden" onchange="selectControllineOffice()" onfocus="return officeCheck()">
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
                                    <td>Controlling OfficeName</td>
                                    <td>
                                        <input type=text name="txtconOfficeName" disabled
                                               size="45">
                                    </td>
                                  </tr>
                                  <!--<tr>
                                    <td class="td">Date Effective From<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                    <td>
                                        <input type=text name="txtDate_Effective_From" maxlength="10" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')">(dd/MM/yyyy)
                                    </td>
                                  
                                  </tr>-->
                                  <!--<tr>
                                        <td rowspan="2">Controlling OfficeAddress
                                                </td>
                                        <td><input type=text name="txtconOfficeAddress" size="45" disabled></td>
                                        
                                  </tr>
                                  <tr>
                                        <td><input type=text name="txtconOfficeAddress1" size="45" disabled></td>
                                        
                                  </tr>-->
                                  <tr>
                                    <td>Controlling Office Address
                                    </td>
                                    <td><textarea cols="30" rows="5" name="txtconOfficeAddress" id="txtconOfficeAddress" disabled></textarea>
                                    </td>
                                    
                                  </tr>
                                  <tr>
                                      <td>Primary Work&nbsp;Nature  
                                      <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                      <td>                                        
                                        <select name="cmbPrimaryID" id="cmbPrimaryID" onfocus="return officeCheck()">                                        
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
                                      <td>Date of Formation</td>
                                      <td>
                                        <input type="text" name="txtDOF" maxlength="10" size="10" onFocus="return officeCheck();javascript:vDateType='3'"  onkeypress="return  calins(event,this)" onblur="return checkdt(this);"/>
                                      <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmOffice.txtDOF);" alt="Show Calendar" ></img>
                                </td>
                                  </tr>
                                  <tr>
                                      <td>
                                        Remarks
                                      </td>
                                      <td>
                                        <textarea name="txtRemarks" cols="25" rows="5" onfocus="return officeCheck()" ></textarea>
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
                                            <td width="43%" rowspan="2">
                                               Address<label style="color:rgb(255,0,0);"> &nbsp;</label>
                                            </td>
                                            <td width="57%">
                                            <input type="text" name="txtAdd1" maxlength="50"
                                                   size="40" onfocus="return officeCheck1()"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="57%">
                                              <input type="text" name="txtAdd2" maxlength="50"
                                                     size="40" onfocus="return officeCheck1()"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="43%">
                                             City / Town
                                            </td>
                                            <td width="57%">
                                              <input type="text" name="txtAdd3" maxlength="50" size="25" onfocus="return officeCheck1()"/>
                                            </td>
                                        </tr> 
                                          
                                         <tr>
                                            <td width="43%">District 
                                            <label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
                                            <td width="57%">
                                              <SELECT size=1 name=cmbDistrict onfocus="return officeCheck1()">
                                                  <option value="0">
                                                                                          ----Select
                                                                                          District----
                                                                                    </option>
                                                  <%
                                                        try
                                                        {
                                                          results=statement.executeQuery("select district_code,district_name from COM_MST_DISTRICTS order by District_Name"); 
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
                                        <tr>
                                            <td width="43%">Pincode</td>
                                            <td width="57%">
                                              <input type="text" name="txtPinCode" maxlength="6" size="6"   onkeypress="return  numbersonly1(event,this)" onchange="return checkpincode();" onfocus="return officeCheck1()"/>
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
                                            <td width="43%">STD Code </td>
                                            <td width="57%">
                                              <input type="text" name="txtSTDCode" maxlength="5" size="6" onkeypress="return  numbersonly1(event,this)" onchange="return checkstd();" onfocus="return officeCheck1()"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">Phone Number </td>
                                            <td width="57%">
                                              <input type="text" name="txtPhoneNo" maxlength="10" size="10" onkeypress="return  numbersonly1(event,this)" onchange="return checkphone();" onfocus="return officeCheck1()"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">Additional Phone Numbers(seperated&nbsp;by&nbsp;,)                                      <label style="color:rgb(255,0,0);"></label></td>
                                            <td width="57%">
                                              <input type="text" name="txtAddPhoneNo"
                                                     maxlength="50" size="30" onkeypress="return  addphone(event)" onchange="return checkaddphone();" onfocus="return officeCheck1()">
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">FAX Number </td>
                                            <td width="57%">
                                              <input type="text" name="txtFAXNo" maxlength="10" size="10" onkeypress="return  numbersonly1(event,this)" onchange="return checkfax();" onfocus="return officeCheck1()"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">Additional FAX Numbers( seperated&nbsp;by&nbsp;,)<label style="color:rgb(255,0,0);"></label></td>
                                            <td width="57%">
                                              <input type="text" name="txtAddFAXNo" maxlength="50" size="30" onkeypress="return  addphone(event)" onchange="return checkfaxno();" onfocus="return officeCheck1()"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">E-Mail&nbsp;ID</td>
                                            <td width="57%">
                                              <input type="text" name="txtEMail" maxlength="75" size="25" onchange="return ValidateForm()" onfocus="return officeCheck1()"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">Additional E-Mail&nbsp;ID
                                            ( seperated&nbsp;by&nbsp;,)<label style="color:rgb(255,0,0);"></label></td>
                                            <td width="57%">
                                              <textarea name="txtAddEMail" cols="25" rows="4" onchange="addcheckemail()" onfocus="return officeCheck1()"></textarea>
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
                <div id="bghead" class="tdH">
                    <center>  
                        <input type="SUBMIT" value="  Submit  " name="cmdSub"> &nbsp;
                        <input type="RESET" value=" Clear All " name="cmdClear" onclick="clear1()">&nbsp; 
                        <input type="BUTTON" value=" Exit " name="cmdCancel" onclick="closeWindow();"> &nbsp;                                                              
                    </center>
                </div>                         
                
        </form>
  </body>
</html>

