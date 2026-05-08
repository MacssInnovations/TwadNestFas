<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Show An Office Details</title>
    <link href='../../../../../css/yellow.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../../../../Library/scripts/selectOfficesample.js"></script>
          <script type="text/javascript" src="../scripts/NewOfficeValidation.js" ></script>
    <script language="javascript" type="text/javascript">
        function nullcheck()
        {      
          if((document.prompt.txtOffice_Id.value=="") || (document.prompt.txtOffice_Id.value.length<=0))
          {
                alert("Please Enter Office ID");
                document.prompt.txtOffice_Id.focus();
                return false;
          } 
        }
    
          function closeWindow()
          {              
              window.open('','_parent','');
              window.close();
              window.opener.focus();
          }          
    </script>
    <!--<link href="../../../../../css/green.css" rel="stylesheet" media="screen"/>-->
    <link href="css/yellow.css" rel="stylesheet" media="screen"/>
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
    <form name="prompt1">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr>
          <td colspan="2" class="bgClass">
            <center>Select an Office </center>
          </td>
        </tr>
        <!--<tr>
          <td width="49%" align="right">Enter The Office Id</td>
          <td width="51%">
            <input type="text" name="txtOffice_Id" maxlength="10" size="10"/>
          </td>
        </tr>-->
        <tr>
          <td width="49%" align="right">Select The Office Type to filter the Offices</td>
          <td width="51%">
            <table>
                                        <tr>
                                            <td>Office Level</td>
                                            <td><div id="divType1" style="visibility:hidden">Office Type</div></td>
                                            <td><div id="divType2" style="visibility:hidden">Select Office</div></td>
                                        </tr>
                                        <tr>                                            
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
                                                        onchange="selectOffice(this.form.name,'txtOffice_Id')">
                                                    <option value=0>----Select Office----</option>                                               
                                                </select>
                                            </td>
                                        </tr>
                                        </table>
          </td>          
        </tr>
        <!--<tr class="bgClass">
          <td align="right" width="49%">
            <input type="submit" value="  Show  "/><img src="css/Sample3.css">
          </td>
          <td width="51%">
            <input type="BUTTON" value="  Cancel  " onclick="closeWindow();"/>
          </td>
        </tr>-->
      </table>
    </form>
  </body>
</html>
