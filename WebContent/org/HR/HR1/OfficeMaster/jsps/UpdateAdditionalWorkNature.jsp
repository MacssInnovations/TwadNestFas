

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Update Additional Work Nature</title>    
    <script type="text/javascript" src="../../../../Library/scripts/selectOffice.js">
    </script>     
    <script type="text/javascript" src="../scripts/AddlWorkNature.js">
    </script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    <link href='../../../../../css/yellow.css' rel='stylesheet' media='screen'/>
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
              //System.out.println("Exception in creating statement:"+e);
       }          
  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  }  
 %>
  <form name="frmClosure" method="POST" action="javascript:void(0);" >
      <table width="100%" align="center">
        <tr>
            <td class="bgClass">
                <center><b>Update Additional Work Nature</b></center>
            </td>
        </tr>
        <tr>
            <td>
                <table cellspacing="3" cellpadding="1" width="100%">
                <tr>
                  <td width="36%">
                    Office&nbsp;ID  
                    <label style="color:rgb(255,0,0);">&nbsp;*</label>
                  </td>
                  <td width="64%">
                    <table>
                                        <tr>
                                            <td></td>
                                            <td>Office Level</td>
                                            <td><div id="divType" style="visibility:hidden">Office Type</div></td>
                                            <td>Select Office</td>
                                        </tr>
                                        <tr>                                            
                                            <td>
                                                <input type="text" name="txtOffice_Id" maxlength="6" size="6"
                                                       onblur="loadOffice(document.frmClosure.txtOffice_Id.value,'nothing');callServer('list');"/>
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
                                                <select name="cmbSelectOffice" id="cmbSelectOffice"
                                                        onchange="selectOffice(this.form.name,'txtOffice_Id');loadOffice(document.frmClosure.txtOffice_Id.value,'nothing');callServer('list');">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>
                                        </tr>
                                        </table>
                  </td>
                </tr>
                <tr>
                  <td  width="36%">
                    Name Of The Office                    
                  </td>
                  <td width="64%">
                    <input type="text" name="txtOfficeName" size="25"
                           disabled="disabled"/>                    
                  </td>
                </tr>
                <tr>
                    <td  width="36%">Office Address</td>
                    <td width="64%">
                        <textarea name="txtOfficeAddress" cols="25" rows="4"
                                  disabled="disabled"></textarea>
                    </td>
                </tr>
                <tr>
                                      <td class="td">Additional Work&nbsp;Nature  
                                      <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                                      <td>                                        
                                        <select name="cmbPrimaryID">                                        
                                            <option value="0">
                                            ----Select Work Nature----
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
                  <td  width="36%">
                    Date Effective From  
                  <label style="color:rgb(255,0,0);">
                    &nbsp;*
                  </label>
                </td>
                  <td width="64%">
                    <input type="text" name="txtEffectiveDate" size="10" maxlength="10"
                           onblur="validate_date('frmClosure','txtEffectiveDate')"/> in the Format DD/MM/YYYY                   
                  </td>
                </tr>               
                <tr>
                    <td  width="36%">Remarks</td>
                    <td width="64%">
                        <textarea name="txtRemarks" cols="25" rows="4"></textarea>
                    </td>
                </tr>
              </table>
          </td>
        </tr>        
        <tr>
                    <td align="left" colspan=2>
                        <hr>Options  :  
                        <input type="button" value="  Add  "
                               onclick="callServer('add');" name="butAdd">
                        <input type="button" value="Delete" disabled="disabled"
                               onclick="callServer('delete');" name="butDelete">
                        <input type="button" value="Cancel" name="butCancel"
                               disabled="disabled"
                               onclick="clearOfficeDetails()">                  
                </td>
        </tr>
        <tr>
            <td colspan=2>
                <table width="100%" border="1">
                    <thead class="bgClass">
                        <tr>
                            <th>
                                Select and
                            </th>
                            <th>
                                Office ID
                            </th>
                            <th>
                                Name of the Office 
                            </th>
                            <th>
                                        Addl Work Nature
                                    </th>
                            <th>
                                        Date Effective From
                                    </th>
                            <th>
                                Remarks
                            </th>
                        </tr>
                    </thead>
                    <tbody id="attachments">
                    </tbody>
                </table>
                    </td>
                </tr>               
              </table>
            </td>
        </tr>        
      </table>
      
    </form>
  </body>
</html>