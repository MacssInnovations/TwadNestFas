<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Upating AccountUnit Details</title>
    <link href="../../../../../css/yellow.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    
    <script type="text/javascript" src="../scripts/controllingOfficeAccountUnit.js"></script>
    <script type="text/javascript" src="../scripts/OfficeAccountUnit.js"></script>
    <script type="text/javascript" src="../scripts/OfficeAccountUnitAddress.js"></script>
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
          </script> 
  </head>
  <body class="bgbody">
  <%
  Connection connection=null;
   Statement statement=null;
   ResultSet results=null;  
   String Account=request.getParameter("Account");
   

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
  
  <form name="frmOffice" method="post" action="../../../../../ServletOfficeAccountingUnit.con?command=Add">
  
                <table  cellspacing="1" cellpadding="3" width="100%" class="bgbody" border="1">
                    <tr>
                        <td class="bgClass">
                            <center>
                            
                            <h3>Update Accounting Unit Details</h3>
                            
                            </center>
                        </td>
                    </tr>
                    <tr>
                        <td>                        
                              <table  cellspacing="3" cellpadding="1"  width="100%">
                                  <TR>
                                      <TD>Office Id</td>
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
                                                <input type="text" name="txtOffice_Id"  onkeyup="isInteger(this,event)" id="txtOffice_Id" onblur="callServer1('Load','null')" tabindex="1" maxlength="4">or Select 
                                            </td>
                                            <td>
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
                                                <select name="cmbSelectOffice" style="visibility:hidden" id="cmbSelectOffice" onchange="selectControllineOffice('office')">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>
                                        </tr>
                                        </table>
                                      </TD>
                                  </TR>
                                  <TR>
                                      <TD><font color="#808080">
                                          Name of the Office</font>
                                      </TD>
                                      <TD>
                                          <input type="text" name="txtOffice_Name" id="txtOffice_Name" disabled >
                                      </TD>
                                  </TR>
                                   <tr>
                                      <td><font color="#808080">Office Address1</font></td>
                                      <td>
                                            <input type="text" name="txtOffice_Address1" id="txtOffice_Address1" disabled>
                                </td>
                                  </tr>
                                  <tr>
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
                                  </tr>
                                  <TR>
                                      <TD>
                                          Is it an Accounting Unit?<br><font size=2>
                                          (Not applicable for Sub Division and Section)</font>
                                      </TD>
                                      <TD>
                                          <input type="radio" value="Y" checked name="account_unit" id="account_unit" onclick="OfficeId1()">Yes
                                         <input type="radio" name="account_unit"  id="account_unit" value="N" onclick="OfficeId()">No 
                                      </TD>
                                  </TR>
                                  <tr>
                                    <td colspan="2">
                                        <div id="account" style="display:none">
                                            <table>
                                            <TR>
                                      <TD>
                                          Accounting Office Id (in case the office is not an accounting unit)

                                      </TD>
                                      <TD>
                                            
                                            <table>
                                        <tr>
                                            <td></td>
                                            <td>Office Level</td>
                                            <td><div id="divType3" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType4" style="visibility:hidden">Select Office</div></td>
                                        </tr>
                                        <tr>                                            
                                            <td>                                            
                                            <input type="text" name="txtAttachedOfficeID" maxlength="6" size="6" id="txtAttachedOfficeID"
                                                   onblur="callServer2('Attach','null')" tabindex="2"/>
                                            </td>
                                            <td>
                                                <SELECT size=1
                                                        name="cmbAttachedOfficeLevel"
                                                        onchange="getAttachOfficesByLevel(this.form.name)">   
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
                                                <select name="cmbAttachOfficeType" style="visibility:hidden"
                                                        onchange="getAttachOfficesByType(this.form.name)">
                                                    <option value=0>
                            ----Select Office Type----
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
                                                        onchange="selectAttachOffice(this.form.name,'txtAttachedOfficeID')">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>
                                        </tr>
                        </table>


                  
                                          
                                      </TD>
                                  </TR>
                                  <TR>
                                      <TD><font color="#808080">
                                          Name of the Office</font>
                                      </TD>
                                      <TD>
                                    <input type="text" name="AccountOffice_Name" id="AccountOffice_Name" disabled/>
                                </td>
                                  </TR>
                                  <tr>
                                      <td><font color="#808080">Office Address1</font></td>
                                      <td>
                                            <input type="text" name="txtAccountOffice_Address1" id="txtAccountOffice_Address1" disabled>
                                </td>
                                  </tr>
                                  <tr>
                                      <td><font color="#808080">Office Address2</font></td>
                                      <td>
                                            <input type="text" name="txtAccountOffice_Address2" id="txtAccountOffice_Address2" disabled>
                                </td>
                                  </tr>
                                  <tr>
                                      <td><font color="#808080">Office Address3</font></td>
                                      <td>
                                            <input type="text" name="txtAccountOffice_Address3" id="txtAccountOffice_Address3" disabled>
                                </td>
                                  </tr>
                                  <tr>
                                    <td>
                                        Date Created
                                    </td>
                                    <td>
                                        <input type=text name=txtDateCreated id=txtDateCreated  maxlength="10" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')" >
                                    </td>
                                    
                                  </tr>
                                  <TR>
                                      <TD>
                                          Remarks
                                      </TD>
                                      <TD>
                                          <textarea rows="4" name="Remarks" id="Remarks" cols="38" ></textarea>
                                      </TD>
                                  </TR>
                                            
                                            </table>
                                        </div>
                                    </td>
                                  </tr>
                                  
                                  
                                  
                                  
                                  <TR>
                                      <TD colspan=2 class="bgClass"><center>
                                         <input type=Submit value=Submit onclick="return nullCheck()" tabindex="5">
                                         <input type=button value=Cancel onclick="closeWindow();">
                                         </center>
                                      </TD>
                                      
                                  </TR>
                                  
                              </table>
                         </td>
                     </tr>
                    </table>
                    
                    
                    
  </form>
  </body>
</html>