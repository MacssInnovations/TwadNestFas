<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
   <script type="text/javascript" src="../scripts/ValidateControllingOffice.js"></script>
   <!--<script type="text/javascript" src="../scripts/NewOfficeValidation.js" ></script>-->
   <script type="text/javascript" src="../scripts/ValidateOfficeTest.js"></script>
   <script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
   <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
   <script type="text/javascript"       src="../scripts/CalendarControl.js"></script>
   <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script>
        function popup()
            {
            
                //alert('hai');
                var url="NewPopupTest.jsp";
                window.open(url,"seloffice","height=300,resizable=yes");
            
            }
            function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    var w=window.open(window.location.href,"_self");
                    w.close();
                    window.opener.focus();
                }
    </script>
    <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
    <link href='../../../../../css/RWS_CSSColour.css' rel='stylesheet' media='screen'/>
    
  </head>
  <body class="table">
  <%
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null;   
   ResultSet rs1=null; 
   int intHeadCode=0,intCOffId=0;
   String strName="",strSName="",strCCAClassId="";
   String strLevel="",strOCode="",strPrimaryId="",strDateOfFormation="",strHRAClassId="",strIsAccountUnit="",strWingsApplicable="",strRemarks="";
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
                            <h3>Validate Office Details</h3>                            
                            </center>
                        </td>
                    </tr>                    
              </table>
  <form action="../../../../../ValidateNewServletUpdateOfficeDetails.con" name="frmOffice" method="POST" onsubmit="return nullcheck()">
  <div class="tab-pane" id="tab-pane-1" >
    <div class="tab-page">
    <h2 class="tab">Office Details</h2>
    <div >
  <table cellspacing="1" cellpadding="3" width="100%">
        <!--<tr>
            <td colspan="2" class="tdH"><center><b>Validate Office Details</b></center></td>
        </tr>-->
        
        <tr>
            <td>Enter Office Id:</td>
            <td><input type=text name="txtOffice_Id" onchange="officedetails(this.value);"  maxlength="4" onkeypress="return  numbersonly1(event,this)">
            <select name="cmbOffice_Id" id="cmbOffice_Id" onchange="callOffice()">
            <option value="0">--Select Office Id--</option>
            <%
                try
                  {
                    results=statement.executeQuery("select office_id,office_name from com_mst_offices_tmp where process_flow_status_id in ('CR','MD') and office_status_id not in('DL') "); 
                    while(results.next()) 
                    {
                        out.print("<option value='" + results.getInt("office_Id") + "'>" + results.getString("office_Name") +"(" +results.getInt("office_id")  + ")" + "</option>");                      
                    }
                    results.close();
                  }
                  catch(Exception e)
                  {
                  System.out.println("exception occured : " + e);
                  }      
            %>
            </select>
            <!--<img src="../../../../../images/c-lovi.gif" onclick="jobpopup()" alt=""></img></td>-->
        </tr>
        <tr>
                                      <td  >Office Name <label style="color:rgb(255,0,0);">&nbsp;*</label> </td>
                                      <td style="color:rgb(255,51,102);">
                                        <input type="text" name="txtOff_Name"
                                               size="40" maxlength="75" onfocus="return officeCheck()">
                                      </td>
                                  </tr>
                                  <tr>
                                      <td  >Office Short Name
                                      <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                      <td>
                                        <input type="text" name="txtShortName"
                                               maxlength="55" size="40" onfocus="return officeCheck()"/>
                                      </td>
                                  </tr> 
                                  
                                  <tr>
                                      <td  >Office&nbsp;Level
                                      <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                      <td>
                                        <SELECT size=1 name=cmbLevelId onchange="checkLevel();setDefaultCadre(this.value);setOfficeLevel();" onfocus="return officeCheck()">   
                                            <option value=0>
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
                                      <td  >Office Head Code
                                      <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                      <td>
                                        <select name="cmbHeadCode" id="cmbHeadCode" onfocus="return officeCheck()">                                        
                                            <option value=0>
                                            ----Select Cadre-------------
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
                                                <input type="text" name="txtContrllingOfficeID" maxlength="70" size="6" onblur="officename()" disabled value=<% if(intCOffId!=0){ %> <%=intCOffId%> <%}%> >                                                 
                                                <input type="hidden" name="txtHContrllingOfficeID" />
                                            </td>
                                            <td>
                                                <SELECT size=1 name=cmbControllingLevel id="cmbControllingLevel" onchange="getOfficesByLevel()" onfocus="return officeCheck()">   
                                                <option value>
                                                        ----Select
                                                        OfficeLevel----
                                                    </option>
                                                <%
                                                      /*try
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
                                                      }*/      
                                                %>
                                              </SELECT>
                                              </td>
                                              <td>
                                                <select name="cmbOfficeType" style="visibility:hidden" onchange="getOfficesByType()" onfocus="return officeCheck()">
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
                                                <select name="cmbSelectOffice" style="visibility:hidden" id="cmbSelectOffice" onchange="selectControllineOffice()" onfocus="return officeCheck()">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>
                                        </tr>
                                        </table>
                                      </td>
                                  </tr>
                                     <tr>
                                    <td>Controlling OfficeName</td>
                                    <td>
                                        <input type=text name="txtconOfficeName" value="<%=strName%>" disabled
                                               size="40">
                                    </td>
                                  </tr>                                                            
                                  <!--<tr>
                                      <td  >Office&nbsp;Old Code                                      <label style="color:rgb(255,0,0);"></label></td>
                                      <td>
                                        <input type="text" name="txtOCode"  maxlength="3" size="3"  onkeyup="isInteger(this,event);" value=<%=strOCode%>  ></td>
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
                                 
                                  <tr>
                                      <td >Primary Work Nature  
                                      </td>
                                      <td>                                        
                                        <select name="cmbPrimaryID" id="cmbPrimaryID" onfocus="return officeCheck()">                                        
                                            <option value="">----Select Work Nature--------</option>
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
                                        <input type="text" name="txtDOF" maxlength="10" size="10"  onFocus="return officeCheck();javascript:vDateType='3'"  onkeypress="return  calins(event,this)" onblur="return checkdt(this);">
                                        <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmOffice.txtDOF);" alt="Show Calendar"></img>
                                        <!--<input type="text" name="txtDOF" maxlength="10" size="10" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')"/>-->
                                      </td>
                                  </tr>
                                  
                                  
                                  <tr>
                                      <td  >
                                        Remarks
                                      </td>
                                      <td>
                                        <textarea name="txtRemarks" cols="25" rows="5" onfocus="return officeCheck()"s><%=strRemarks%></textarea>
                                      </td>
                                  </tr>
                               <!--   <tr>
                                         <td colspan="2" class="tdH"><b>Validation Details</b></td>
                                </tr>
                                <tr>
                                    <td>Record Status</td>
                                    <td>
                                        <select id="cmbRecordStatus" name="cmbRecordStatus" onfocus="return officeCheck()" disabled>
                                        <option value="0">--Select Record Status--</option>
                                        <%
                                                  try
                                                  {
                                                    results=statement.executeQuery("select * from COM_MST_PROCESS_FLOW where process_flow_status_id in('MD','VR','FR') order by process_flow_status_desc"); 
                                                    while(results.next()) 
                                                    {
                                                        out.print("<option value='" + results.getString("PROCESS_FLOW_STATUS_ID") + "'>" + results.getString("PROCESS_FLOW_STATUS_DESC") + "</option>");                      
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
                                      <td colspan=3 class="tdH" align="center">
                                         
                                          <input type="SUBMIT" value="  Validate  " name="cmdSub"> &nbsp;
                                          <input type="RESET" value=" Clear All " name="cmdClear" onclick="funclear1()">&nbsp; 
                                          <input type="Button" value=" Exit " name="cmdCancel" onclick="closeWindow();"> &nbsp;                                                              
                                        
                                      </td>                              
                                  </tr>-->
            
  </table>
  </div>
                              </div>
                              <div class="tab-page">
                        <h2 class="tab">Contact Details</h2>
                        <div >
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
                                                   size="40" onfocus="return officeCheck()"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="57%">
                                              <input type="text" name="txtAdd2" maxlength="50"
                                                     size="40" onfocus="return officeCheck()"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="43%">
                                             City / Town
                                            </td>
                                            <td width="57%">
                                              <input type="text" name="txtAdd3" maxlength="50" size="25" onfocus="return officeCheck()"/>
                                            </td>
                                        </tr> 
                                          
                                         <tr>
                                            <td width="43%">District 
                                            <label style="color:rgb(255,0,0);"> &nbsp;*</label></td>
                                            <td width="57%">
                                              <SELECT size=1 name=cmbDistrict onfocus="return officeCheck()">
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
                                              <input type="text" name="txtPinCode" maxlength="6" size="6"   onkeypress="return  numbersonly1(event,this)" onchange="return checkpincode();" onfocus="return officeCheck()"/>
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
                                              <input type="text" name="txtSTDCode" maxlength="5" size="6" onkeypress="return  numbersonly1(event,this)" onchange="return checkstd();" onfocus="return officeCheck()"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">Phone Number </td>
                                            <td width="57%">
                                              <input type="text" name="txtPhoneNo" maxlength="10" size="10" onkeypress="return  numbersonly1(event,this)" onchange="return checkphone();" onfocus="return officeCheck()"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">Additional Phone Numbers(seperated&nbsp;by&nbsp;,)                                      <label style="color:rgb(255,0,0);"></label></td>
                                            <td width="57%">
                                              <input type="text" name="txtAddPhoneNo"
                                                     maxlength="50" size="30" onkeypress="return  addphone(event)" onchange="return checkaddphone();" onfocus="return officeCheck()">
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">FAX Number </td>
                                            <td width="57%">
                                              <input type="text" name="txtFAXNo" maxlength="10" size="10" onkeypress="return  numbersonly1(event,this)" onchange="return checkfax();" onfocus="return officeCheck()"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">Additional FAX Numbers( seperated&nbsp;by&nbsp;,)<label style="color:rgb(255,0,0);"></label></td>
                                            <td width="57%">
                                              <input type="text" name="txtAddFAXNo" maxlength="50" size="30" onkeypress="return  addphone(event)" onchange="return checkfaxno();" onfocus="return officeCheck()"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">E-Mail&nbsp;ID</td>
                                            <td width="57%">
                                              <input type="text" name="txtEMail" maxlength="75" size="25" onchange="checkemail()" onfocus="return officeCheck()"/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td width="43%">Additional E-Mail&nbsp;ID
                                            ( seperated&nbsp;by&nbsp;,)<label style="color:rgb(255,0,0);"></label></td>
                                            <td width="57%">
                                              <textarea name="txtAddEMail" cols="25" rows="4" onchange="addcheckemail()" onfocus="return officeCheck()"></textarea>
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
                        <input type="SUBMIT" value="  Validate  " name="cmdSub"> &nbsp;
                        <input type="RESET" value=" Clear All " name="cmdClear" onclick="funclear1()">&nbsp; 
                        <input type="BUTTON" value=" Exit " name="cmdCancel" onclick="closeWindow();"> &nbsp;                                                              
                    </center>
                </div>        
  
      <p>
        &nbsp;
      </p>
    </form>
  </body>
</html>