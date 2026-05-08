<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>My Profile</title>
    <!-- contains officeSelection -->
    <script type="text/javascript" src="../../../../Library/scripts/selectOffice1.js"></script>
    <!-- contains onBlur functionality -->
    <script type="text/javascript" src="../scripts/OfficeAddressLoad.js"></script>
    
 <!--   <script type="text/javascript" src="../scripts/selectAttachedOffice.js"></script>-->
    
     <script type="text/javascript" src="../scripts/ClosureOfOfficeValids.js"></script>
     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
          </script> 
    
     
    <link href="../../../../../css/testing.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/yellow.css" rel="stylesheet" media="screen"/>
    <link href="../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  <!--  <script type="text/javascript" src="../scripts/UpadeOldRecAjax.js" ></script>-->
    
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    
  </head>
<!--  <body onkeyup="testKeyListener(event)" onload="divposition()" class="bgbody" onclick="popupdisable()"> -->
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
              //System.out.println("Exception in creating statement:"+e);
       }          
  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  }
  
  %>


<form name="UpdateOldRecForm" method="POST" action="../../../../../ServletUpdateOldRec.con" >
<table width=100% cellpadding="3" cellspacing="1" border="1" align="center">
 <tr >
    <td class="bgClass">
        <center><h3>Update Old Record Maintenance</h3></center>
    </td>
 </tr>
 <tr>
    <td>                        
        <table  cellspacing="3" cellpadding="1"  width="101%" >
             <tr>
                <td width="23%">
                    Existing Office Id<label style="color:rgb(255,0,0);">*</label>
                </td>
                <td width="77%">
                   <table>
                                        <tr>
                                            <td></td>
                                            <td>Office Level</td>
                                            <td><div id="divType" style="visibility:hidden">Office Type</div></td>
                                             <td><div id="divType1" style="visibility:hidden">Select Office</div></td>
                                        </tr>
                                        <tr>                                            
                                            <td>
                                                <input type="text" name="txtOffice_Id" maxlength="6" size="6"
                                                       onblur="loadOffice(document.UpdateOldRecForm.txtOffice_Id.value,'nothing')"/>
                                            </td>
                                            <td>
                                                <SELECT size=1 name=cmbOfficeLevel onchange="getOfficesByLevel(this.form.name)">   
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
                                                <select name="cmbOfficeType" style="visibility:hidden" onchange="getOfficesByType(this.form.name)">
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
                                                <select name="cmbSelectOffice" id="cmbSelectOffice" style="visibility:hidden"
                                                        onchange="selectOffice(this.form.name,'txtOffice_Id');loadOffice(this.value)">
                                                    <option value=0>---Select Office----</option>                                               
                                                </select>
                      </td>
                                        </tr>
                                        </table>
                </td>
             </tr>
             <tr>
                <td width="23%">
                    <font color="#808080">Name of the Exiting Office</font>
                </td>
                <td width="77%">
                    <input type="text" name="txt_ExtOffice_Name" id="txt_ExtOffice_Name" disabled>
                </td>
             </tr>
             <tr>
                <td width="23%">
                    <font color="#808080">Office Address1</font>
                </td>
                <td width="77%">
                    <input type="text" name="txt_ExtOffice_Address1" id="txt_ExtOffice_Address1" disabled>
                </td>
             </tr>
             <tr>
                <td width="23%">
                    <font color="#808080">Office Address2</font>
                </td>
                <td width="77%">
                    <input type="text" name="txt_ExtOffice_Address2" id="txt_ExtOffice_Address2" disabled>
                </td>
             </tr>
             <tr>
                <td width="23%">
                    <font color="#808080">City/Town</font>
                </td>
                <td width="77%">
                    <input type="text" name="txt_ExtOffice_City" id="txt_ExtOffice_City" disabled>
                </td>
            </tr>
            <tr>
                <td width="23%" height="31">
                    <font color="#808080">District</font>
                </td>
                <td width="77%" height="31">
                    <select name="cmb_ExtDistrict" id="cmb_ExtDistrict" disabled>
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
                                                            System.out.println("exception occured : " + e);
                                                        }      
                                                  %>
                    </select>
                </td>
            </tr>
            <tr>
                    <td colspan="2" class="bgClass" height="21">
                    Details of Closed Offices&nbsp;whose&nbsp;old&nbsp;Records&nbsp;are Maintained by&nbsp;the&nbsp;above&nbsp;Office
                    </td>
            </tr>
            <tr>
                <td width="23%">
                    Closed Office Id<label style="color:rgb(255,0,0);">*</label>
                </td>
                <td width="77%">
                   <table width="464">
                                        <tr>
                                            <td width="90"></td>
                                            <td width="347">&nbsp;Select&nbsp;a&nbsp;Closed&nbsp;Office&nbsp;</td>
                                            
                                        </tr>
                                        <tr>                                            
                                            <td width="90">                                            
                                            <input type="text" name="txtAttachedOfficeID" maxlength="6" size="6"
                                                   onblur="loadOfficeSecond(this.value)"/>
                                            </td>
                                            <td width="347">
                                                <SELECT size=1 id="cmbAttachedOffice"
                                                        name="cmbAttachedOffice"
                                                        onchange="loadOfficeSecond(this.value)">   
                                                <option value=0>
                            ----Select OfficeLevel----
                          </option>
                                                 <%
                                                        try
                                                        {
                                                          results=statement.executeQuery("select office_id,office_name,status_effective_from from com_mst_offices where office_status_id='CL'");
                                                          while(results.next()) 
                                                          {
                                                              out.print("<option value='" + results.getInt(1) + "'>" + results.getString(2) + "</option>");                      
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
                </td>
             </tr>
             <tr>
                <td width="23%">
                    <font color="#808080">Name of the Closed Office</font>
                </td>
                <td width="77%">
                    <input type="text" name="txt_ClsOffice_Name" id="txt_ClsOffice_Name"  >
                </td>
             </tr>
             <tr>
                <td width="23%">
                    Closure Date<label style="color:rgb(255,0,0);">*</label>
                </td>
                <td width="77%">
                    <input type=text name="txt_DateClosure" id="txt_DateClosure" onchange="return validate_date('frmOffice','txt_DateClosure')" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')">
                </td>
             </tr>
             <tr>
                <td width="23%">
                    Records&nbsp;Handed&nbsp;over Date<label style="color:rgb(255,0,0);">*</label>
                </td>
                <td width="77%">
                   <input type=text name="txt_DateHandover" id="txt_DateHandover" onchange="return validate_date('frmOffice','txt_DateHandover')" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')">
                </td>
             </tr>
            <tr>
                <td width="23%">Remarks</td>
                <td width="77%">
                 <textarea rows="4" name="txt_Remark" id="txt_Remark" cols="38" ></textarea>
                </td>
             </tr>
             <tr>
                <td colspan=2 class="bgclass">
                                          <input type="Button" value="  Add " id="Add" onclick="callServer1('Add','null')" name="cmdAdd" > 
                                          <input type="Button" value=" Update" onclick="callServer1('Update','null')" disabled name="cmdUpdate">
                                          <input type="Button" value=" Delete" id="Revoke" onclick="callServer1('Delete','null')" disabled name="cmdDelete">
                                          <input type="Button" value="Clear All" onclick="clearAllWing()" name="cmdClearAll">                                 
                                      </td>
            </tr>
            <tr>
                <td colspan=2>
                </td>
            </tr>
        </table>
         <table name="Existing" id="Existing"  border="1" width="100%"  style="font-family:arial;">
                                    <tr>
                                            <td colspan=7 class="bgClass">
                                                Existing Details
                                            </td>
                                    </tr>
                                    
                                    <tr>
                                        <th>
                  View
                </th>
                                      <th>SL No</th>
                                        <th>Closed Office Id</th>
                                        <th>Closed Office Name</th>
                                        <th>Closure Date</th>
                                        <th>Records Handed Over Date</th>
                                        <th>Remarks</tr>
                                   
                                    <tbody id="tblList" name="tblList">
                                    </tbody>
                                    <tr>
                                        <td colspan=7 class="bgClass"><center>
                                            <input type=Submit value=Submit onclick="return nullcheck()"/>
                                            <input type=button value=Cancel onclick="closeWindow();" /></center>
                                        </td>
                                    </tr>
                                  </table>
            
</table>
</form>
</body>
</html>