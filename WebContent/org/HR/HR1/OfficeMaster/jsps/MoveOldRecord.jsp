<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Move Old Record</title>
    <!-- contains officeSelection -->
    <script type="text/javascript" src="../../../../Library/scripts/selectOffice1.js"></script>
    <script type="text/javascript" src="../../../../Library/scripts/selectOffice2.js"></script>
    <!-- contains onBlur functionality -->
    <script type="text/javascript" src="../scripts/OfficeAddressLoad.js"></script>
    <script type="text/javascript" src="../scripts/OfficeAddressLoad2.js"></script>
 <!--   <script type="text/javascript" src="../scripts/selectAttachedOffice.js"></script>-->
    
     <script type="text/javascript" src="../scripts/MoveOldRecValids.js"></script>
    
     
    <link href="../../../../../css/testing.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/yellow.css" rel="stylesheet" media="screen"/>
    <link href="../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  <!--  <script type="text/javascript" src="../scripts/UpadeOldRecAjax.js" ></script>-->
    
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


<form name="UpdateOldRecForm" method="POST" action="../../../../../ServletMovOldRec.con" >
<table width=100% cellpadding="3" cellspacing="1" border="1" align="center">
 <tr >
    <td class="bgClass">
        <center><h3>Move Old Record Maintenance</h3></center>
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
                                            <td>&nbsp;</td>
                                            <td>Office Level</td>
                                            <td><div id="divType" style="visibility:hidden">Office Type</div></td>
                                             <td><div id="divType1" style="visibility:hidden">Select Office</div></td>
                                        </tr>
                                        <tr>                                            
                                            <td>
                                                <input type="text" name="txtOffice_Id" maxlength="6" size="6"
                                                       onblur="load_Closed_Office(this.value);loadOffice(document.UpdateOldRecForm.txtOffice_Id.value,'nothing')"/>
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
            </tr>
         <!--    <tr>
                    <td colspan="2" class="bgClass" height="21">
                    Details of Closed Offices&nbsp;whose&nbsp;old&nbsp;Records&nbsp;are Maintained by&nbsp;the&nbsp;above&nbsp;Office
                    </td>
            </tr>
            
           
            
            <tr>
                <td width="23%">
                    Old Office Id<label style="color:rgb(255,0,0);">*</label>
                </td>
                <td width="77%">
                   <table width="464">
                                        <tr>
                                            <td width="90">&nbsp;</td>
                                            <td width="347">&nbsp;Select&nbsp;a&nbsp;Closed&nbsp;Office&nbsp;</td>
                                            
                                        </tr>
                                        <tr>                                            
                                            <td width="90">                                            
                                            <input type="text" name="txtAttachedOfficeID" maxlength="6" size="6"
                                                   onblur="load_Closed_Office(this.value)"/>
                                            </td>
                                            <td width="347">
                                                <SELECT size=1 id="cmbAttachedOffice"
                                                        name="cmbAttachedOffice"
                                                        onchange="loadOfficeForMoveOldRec(this.value)">   
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
                    <font color="#808080">Name of the Old Office</font>
                </td>
                <td width="77%">
                    <input type="text" name="txt_ClsOffice_Name" id="txt_ClsOffice_Name" disabled>
                </td>
             </tr>
             <tr>
                <td width="23%">
                    <font color="#808080">Date of Closure</font>
                </td>
                <td width="77%">
                    <input type=text name="txt_DateClosure" id="txt_DateClosure" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')" disabled>
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
                <td width="23%"><font color="#808080">Remarks</font></td>
                <td width="77%">
                 <textarea rows="4" name="txt_Remark" id="txt_Remark" cols="38" disabled ></textarea>
                </td>
             </tr>
         -->
          
          <tr>
          <td colspan=2>
          
          
          <table name="ListClosed" id="ListClosed"  border="1" width="100%"  style="font-family:arial;">
                                    <tr>
                                            <td colspan=7 class="bgClass" height="21">
                                                Details of Closed Offices&nbsp;whose&nbsp;old&nbsp;Records&nbsp;are Maintained by&nbsp;the&nbsp;above&nbsp;Office
                                            </td>
                                    </tr>
                                    
                                    <tr>
                                        <th>
                  View
                </th>
                                      <th>SL No</th>
                                        <th>
                  Old Office Id
                </th>
                                        <th>
                          Old Office Name
                        </th>
                                        <th>
                          Date of Closure
                        </th>
                                      <!--  <th>Records Shifted to Which Office</th>-->
                                    
                        </tr>
                                   
                                    <tbody id="tb_List" name="tb_List">
                                    </tbody>
          
          
          </table>
          </td>
          </tr>
       <!--    <tr>
           <td>
                  <input type="checkbox" name="Check1"/>
                </td>
           </tr> -->
            
              <tr>
                    <td colspan="2" class="bgClass" height="21" style="font-family:arial;">
                    Details of an Office to which the Records to be Moved
                    </td>
            </tr>
           
          
             <tr>
                <td width="23%">
                    Records&nbsp;Shifted&nbsp;to Office Id<label style="color:rgb(255,0,0);">*</label>
                </td>
                <td width="77%">
                   <table>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>Office Level</td>
                                            <td><div id="divType3" style="visibility:hidden">Office Type</div></td>
                                             <td><div id="divType4" style="visibility:hidden">Select Office</div></td>
                                        </tr>
                                        <tr>                                            
                                            <td>
                                                <input type="text" name="txtOffice_Id_two" maxlength="6" size="6" onblur="loadOffice_new(document.UpdateOldRecForm.txtOffice_Id_two.value,'nothing');"/>
                                            </td>
                                            <td>
                                                <SELECT size=1
                                                        name="cmbOfficeLevel_two" onchange="getOfficesByLevel_two(this.form.name)">   
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
                                                <select name="cmbOfficeType_two" style="visibility:hidden" onchange="getOfficesByType_two(this.form.name)">
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
                                                <select name="cmbSelectOffice_two" id="cmbSelectOffice_two" style="visibility:hidden"
                                                        onchange="selectOffice_two(this.form.name,'txtOffice_Id_two');loadOffice_new(document.UpdateOldRecForm.txtOffice_Id_two.value,'nothing')">
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
                    <input type="text" name="txt_ExtOffice_Name_two" id="txt_ExtOffice_Name_two" disabled>
                </td>
             </tr>
             <tr>
                <td width="23%">
                    <font color="#808080">Office Address1</font>
                </td>
                <td width="77%">
                    <input type="text" name="txt_ExtOffice_Address1_two" id="txt_ExtOffice_Address1_two" disabled>
                </td>
             </tr>
             <tr>
                <td width="23%">
                    <font color="#808080">Office Address2</font>
                </td>
                <td width="77%">
                    <input type="text" name="txt_ExtOffice_Address2_two" id="txt_ExtOffice_Address2_two" disabled>
                </td>
             </tr>
             <tr>
                <td width="23%">
                    <font color="#808080">City/Town</font>
                </td>
                <td width="77%">
                    <input type="text" name="txt_ExtOffice_City_two" id="txt_ExtOffice_City_two" disabled>
                </td>
            </tr>
            <tr>
                <td width="23%" height="31">
                    <font color="#808080">District</font>
                </td>
                <td width="77%" height="31">
                    <select name="cmb_ExtDistrict_two" id="cmb_ExtDistrict_two" disabled>
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
            </tr>
            <tr>
                <td width="23%">
                    Date of Shifting<label style="color:rgb(255,0,0);">*</label>
                </td>
                <td width="77%">
                    <input type=text name="txt_DateShifting" id="txt_DateShifting" onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')">
                </td>
             </tr>
           
           
           <tr>
                                       <td colspan="2" class="bgClass" height="21" style="font-family:arial;"><center>
                                            <input type=Submit value=Submit onclick="return nullcheck()"/>
                                            <input type=button value=Cancel onclick="closeWindow();" /></center>
                                        </td>
                                    </tr>
             
        </table>
         
</table>
</form>
</body>
</html>