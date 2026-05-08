<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Office Contact Details</title>
  <link href="../../../../../css/testing.css" rel="stylesheet" media="screen"/>
  <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <!--<script type="text/javascript" src="../scripts/OfficeContact.js"></script>
    <script type="text/javascript" src="../scripts/AjaxOfficeContact.js"></script>
    <script type="text/javascript" src="../scripts/OfficeContactAddress.js"></script>-->
    <script type="text/javascript" src="../scripts/AjaxOfficeContactId.js"></script>
    <!--<script type="text/javascript" src="../scripts/controllingOfficeContact.js"></script>-->
    <!--<script type="text/javascript" src="../../../../Library/scripts/selectOffice.js"></script>-->
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
  <form name="frmOffice" action="../../../../../OfficeContact.con" method="Post">
                
                <table  cellspacing="1" cellpadding="3" width="100%"  border="1" align="center" >
                    <tr>
                        <td class="tdH">
                            <center>
                            
                            <h3>Update Office Contact Details</h3>
                            
                            </center>
                        </td>
                    </tr>
                    <tr>
                        <td>                        
                              <table  cellspacing="3" cellpadding="1"  width="100%" >
                                  <TR>
                                      <TD>
                                          Office Id
                                      </TD>
                                      <TD>                                        
                                          
                                        <table>
                                        <!--<tr>                                       
                                            <td>&nbsp;</td>
                                            <td>Office Level</td>
                                            <td><div id="divType1" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType2" style="visibility:hidden">Select Office</div></td>
                                            
                                        </tr>-->
                                        <tr>
                                            <td>
                                                <input type="text" name="txtOffice_Id"  id="txtOffice_Id" onchange="callServer1('Load','null');checkoffice();" tabindex="1" maxlength="4" onkeypress="return  numbersonly1(event,this)">  
                                                <img src="../../../../../images/c-lovi.gif" onclick="jobpopup()" alt=""></img>
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
                                                <select name="cmbSelectOffice"  style="visibility:hidden" id="cmbSelectOffice" onchange="selectControllineOffice('office')">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>-->
                                        </tr>
                                        </table>
                                      </td>
                                  </tr>
                                  <TR>
                                      <TD><font color="#808080">
                                          Name of the Office</font>
                                      </TD>
                                      <TD>
                                          <input type="text" name="txtOffice_Name" id="txtOffice_Name"  size="40" disabled>
                                      </TD>
                                  </TR>
                                   <TR>
                                      <TD rowspan="2">
                                          Office Address<label style="color:rgb(255,0,0);">*</label>
                                </TD>
                                      <TD>
                                          <input type="text" name="txtOffice_Address1" id="txtOffice_Address1" tabindex="2" size="30" onfocus="return officeCheck()">
                                          
                                      </TD>
                                  </TR>
                                  <TR>
                                      <TD>
                                          <input type="text" name="txtOffice_Address2" id="txtOffice_Address2" tabindex="3" onfocus="return officeCheck()" size="30">
                                          
                                      </TD>
                                  </TR>
                                  <TR>
                                      <TD>
                                          City/Town
                                      </TD>
                                      <TD>
                                          <input type="text" name="txtOffice_City" id="txtOffice_City" tabindex="4" onfocus="return officeCheck()">
                                      </TD>
                                  </TR>
                                  <TR>
                                      <TD>
                                          District<label style="color:rgb(255,0,0);">*</label>
                                      </TD>
                                      <TD>
                                          <select name="cmbDistrict" id="cmbDistrict" tabindex="5" onfocus="return officeCheck()">
                                          <option>----Select District----</option>
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
                                      </TD>
                                  </TR>
                                  <tr>
                                    <td>PinCode</td>
                                    <td><input type=text name="txtPin_Code" size=10 maxlength="6" tabindex="6" onfocus="return officeCheck()" onkeypress="return  numbersonly1(event,this)" onblur="return checkpincode();return pinlength();" >
                                    
                                  </tr>
                                  <TR>
                                      <TD>
                                          STD Code
                                      </TD>
                                      <TD>
                                          <input type=text name="txtStd_Code" onkeypress="return  numbersonly1(event,this)" id="txtStd_Code" tabindex="7" maxlength="5" onfocus="return officeCheck()" onchange="return checkstd();">
                                      </TD>
                                  </TR>
                                  <TR>
                                      <TD>
                                          Phone No
                                      </TD>
                                      <TD>
                                          <input type=text name="txtPhone_No" onkeypress="return  numbersonly1(event,this)" id="txtPhone_No" tabindex="8" maxlength="10" onfocus="return officeCheck()" onchange="return checkphone();">
                                      </TD>
                                  </TR>
                                  <TR>
                                      <TD>
                                          Additional Phone No
                                      </TD>
                                      <TD>
                                          <input type=text name="txtAdd_Phone_No" size="40" onkeypress="return  addphone(event)" id="txtAdd_Phone_No" tabindex="9" onfocus="return officeCheck()" onchange="return checkaddphone();">
                                      </TD>
                                  </TR>
                            <TR>
                                      <TD>
                                          FaxNo
                                      </TD>
                                      <TD>
                                          <input type=text name="txtFax_No" onkeypress="return  numbersonly1(event,this)" id="txtFax_No" tabindex="10" maxlength="10" onfocus="return officeCheck()" onchange="return checkfax();">
                                      </TD>
                                  </TR>
                            <TR>
                                      <TD>
                                         Additional FaxNo
                                      </TD>
                                      <TD>
                                          <input type=text name="txtAdd_Fax_No" size="40" onkeypress="return  addphone(event)" id="txtAdd_Fax_No" tabindex="11" onfocus="return officeCheck()" maxlength="50" onchange="return checkfaxno();">
                                      </TD>
                                  </TR>
                            <tr>
                                <td>Email Id</td>
                                <td>
                                    <input type="text" name="txtEmailId"  onchange="return ValidateForm()" id="txtEmailId" tabindex="12" onfocus="return officeCheck()" size="30"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Additional Email Id</td>
                                <td>
                                    <textarea rows="4" name="txtAdd_EmailId"  cols="38" onchange="return addcheckemail()" id="txtAdd_EmailId" tabindex="13" onfocus="return officeCheck()"></textarea>
                                </td>
                            </tr>
                            <TR>
                                      <TD colspan=2 class="tdH"><center>
                                         <input type=Submit value=Submit onclick="return nullcheck()" name=cmdSub tabindex="14">
                                         <input type=reset value="Clear All" id="cmdClear" name="cmdClear" onclick="funclear()">
                                         <input type=button value=Exit onclick="closeWindow();" tabindex="15">
                                         </center>
                                      </TD>
                                      
                                  </TR>
                        </table>
                         </td>
                     </tr>
                    </table>
            
        </form>
        
        <div id="popup" class="popupdiv">
    </div>
                                          
  </body>
  
  
</html>