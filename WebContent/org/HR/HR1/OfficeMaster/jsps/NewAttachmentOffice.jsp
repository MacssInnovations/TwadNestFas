

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Attachment of an office</title>    
    <script type="text/javascript" src="../../../../Library/scripts/selectOffice.js">
    </script>
    <script type="text/javascript" src="../scripts/selectAttachedOffice.js">
    </script>
    <script type="text/javascript" src="../scripts/NewAttachmentOffice.js">
    </script>
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
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
 <%
               /* System.out.println("hai");
                UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                System.out.println("emp"+empProfile);
                int Emp_Id=empProfile.getEmployeeId();
                String Level=empProfile.getOfficeLevel();
                
                System.out.println("the emp id is---->>   "+Emp_Id);
                System.out.println("the Level is---->>   "+Level);*/
%>
  <form name="frmClosure" method="POST" action="" onsubmit="return nullCheckAttachment()">
      <table width="100%" align="center">
        <tr>
            <td class="bgClass">
                <center><b>Attachment of an Office</b></center>
            </td>
        </tr>
        <tr>
            <td>
                <table cellspacing="3" cellpadding="1" width="100%">
                <tr>
                  <td width="36%">
                    Office Being Attached  
                    <label style="color:rgb(255,0,0);">&nbsp;*</label>
                  </td>
                  <td width="64%">
                    <table>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>Office Level</td>
                                            <td><div id="divType1" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType2" style="visibility:hidden">Select Office</div></td>
                                        </tr>
                                        <tr>                                            
                                            <td>
                                                <input type="text" name="txtOffice_Id" maxlength="6" size="6"
                                                       onblur="loadOffice(document.frmClosure.txtOffice_Id.value,'nothing');OfficeLevel()"/>
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
                                                <select name="cmbSelectOffice" id="cmbSelectOffice" style="visibility:hidden" 
                                                        onchange="selectOffice(this.form.name,'txtOffice_Id');loadOffice(document.frmClosure.txtOffice_Id.value,'nothing');">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>
                                        </tr>
                                        </table>
                  </td>
                </tr>
                <tr>
                  <td  width="36%">
                    Name Of The Office To Be Attached                    
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
                <!--<tr>
                    <td>Date of Attachment
                    </td>
                    <td>
                        <input type=text name="txtDateOfAttachment" id="txtDateOfAttachment" disabled>
                    </td>
                </tr>-->
                <tr>
                  <td  width="36%">
                    Date of Attachment  
                  <label style="color:rgb(255,0,0);">
                    &nbsp;*
                  </label>
                </td>
                  <td width="64%">
                    <input type="text" name="txtDateOfAttachment" size="10" maxlength="10"
                           onFocus="javascript:vDateType='3'" onKeyUp="DateFormat(this,this.value,event,false,'3')" onBlur="DateFormat(this,this.value,event,true,'3')"/> (dd/MM/yyyy)                   
                  </td>
                </tr>
                <!--<tr>
                    <td>Name of the Office<label style="color:rgb(255,0,0);">
                    &nbsp;*
                  </label></td>
                    <td>
                    <input type=text name="txtNewOfficeName" id="txtNewOfficeName" size="40">
                    </td>
                </tr>
                <tr>
                    <td>New Office Address</td>
                    <td>
                    <textarea name="txtNewOfficeAddress" id="txtNewOfficeAddress" rows="5" cols="20">
                    </textarea>
                    </td>
                </tr>-->
                <tr>
                    <td  width="36%">New Controlling Office<label style="color:rgb(255,0,0);">
                    &nbsp;*
                  </label></td>
                    <td width="64%">
                    <SELECT size=1 name="cmbLevelId" id="cmbLevelId">   
                                            <option value="0">
                                                       ----Select OfficeLevel----
                                                  </option>
                                             
                                          </SELECT>
                    </td>
                </tr>
                <!--<tr>
                <td>New Controlling Office Name </td>
                <td>
                    <input type=text name="txtAttachedOfficeName">
                </td>
                </tr>-->
                <tr>
                    <td>New Nature of Work <label style="color:rgb(255,0,0);">
                    &nbsp;*
                  </label> </td>
                    <td>
                    <select name="cmbSecondaryID" id="cmbSecondaryID">                                        
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
              </table>
          </td>
        </tr>        
        <tr>
        <td align="center" class="bgClass">
            <input type="submit" value="Submit" name=submit>
            <input type="button" value="Cancel" onclick="closeWindow()">
        </td>
        </tr>
      </table>
      
    </form>
  </body>
</html>