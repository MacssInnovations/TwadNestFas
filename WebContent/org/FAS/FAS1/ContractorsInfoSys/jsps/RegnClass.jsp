
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="../scripts/AjaxRegnClass.js"></script>
    <title>Master Registration Class Details</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/Sample.css" rel="stylesheet" media="screen"/>
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
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
    try
    {
      statement=connection.createStatement();
    }
    catch(SQLException e)
    {
    }
  }
  catch(Exception e)
  {
  }
  %>
  <form name="ClassForm" id="ClassForm">
      <table cellspacing="2" cellpadding="3" border="1" width="75%"
             align="center" class="table">
        <tr>
          <td colspan="2" class="tdH">
            <div align="center">
              <strong>CLASS OF REGISTRATION DETAILS</strong>
            </div></td>
        </tr>
        <tr>
          <td class="table">Registartion Class Id</td>
          <td class="table">
            <input type="text" name="txtClassId" maxlength="2"
                   id="txtClassId" onblur="checkForRedundancy()" onkeyup="isInteger(this,event)" size="5"/>
                   <input type="HIDDEN" name="htxtClassId" maxlength="2"
                   id="htxtClassId"/>
          </td>
        </tr>
        <tr>
          <td class="table">Registration Class Description</td>
          <td class="table">
            <input type="text" name="txtClassName" size="10" maxlength="10"
                   id="txtClassName"/>
          </td>
        </tr>
        <tr>
          <td colspan="2" class="table">
            <input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdDelete" value="DELETE"
                   id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll()"/>
           
          </td>
        </tr>
      </table>
      <table cellspacing="3" cellpadding="2" border="1" width="75%"
             align="center" >
        <tr>
          <td class="table">Existing Details</td>
        </tr>
      </table>
      <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="75%"
             align="center">
        <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Registration Class Id
          </th>
          <th>
            Registration Class Name
          </th>
          </tr>
          <tbody id="tblList" class="table">
           <%
      
          try
          {
              
            String sql="select * from PMS_MST_CON_CLASS ORDER BY REGN_CLASS_ID";
             results=statement.executeQuery(sql);
            try
            {
              while(results.next())
              {
                int strClassId=results.getInt("REGN_CLASS_ID");
	              String strClassName=results.getString("REGN_CLASS_DESC");
                out.println("<tr id='" + strClassId + "'>");   
                out.println("<td><a href=\"javascript:loadValuesFromTable('" + strClassId + "')\">Edit</a></td>");
                out.println("<td>" + strClassId + "</td>");
	              out.println("<td>" + strClassName + "</td></tr>");
                 
              }
            }
            catch(SQLException e)
            {
              System.out.println("Exception in resultset:"+e);
            }
            finally
            {
              results.close();
            }
          }
          
      
      catch(SQLException e)
      { 
        System.out.println("Exception :"+e);
      }
    %>
          
          </tbody>
          
      </table>
      <table id="Exit" cellspacing="2" cellpadding="3" border="1" width="75%"
             align="center">
        <tr>
          
          <td class="tdH">
          <div align="center">
          <input type="button" name="CmdExit" value="EXIT"
                   id="CmdExit" onclick="Exit()" align="middle"/>
          </div>                   
          </td>
          </tr>
      </table>
     
    </form></body>
</html>
