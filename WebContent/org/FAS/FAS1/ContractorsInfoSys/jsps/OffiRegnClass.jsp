
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="../scripts/AjaxOffiRegnClass.js"></script>
    <title>Master Office Registration Class Details</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <body onload="loadDate();displayDetails();">
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
  <form name="OffRegnClassForm" id="OffRegnClassForm">
      <table cellspacing="2" cellpadding="3" border="1" width="75%"
             align="center" class="table">
        <tr>
          <td colspan="2" class="tdH">
            <div align="center">
              <strong>OFFICE REGISTRATION ASSOCIATION DETAILS</strong>
            </div></td>
        </tr>
        <tr>
          <td class="table">Office Level Id</td>
          <td class="table">
          <input type="HIDDEN" value="hseq" id="hseq" name="hseq"/>
           <input type="HIDDEN" value="hlevel" id="hlevel" name="hlevel"/>
          
            <select name="txtofflevel" size="1">
              <option value>--Select Here--</option>
              <%
              try
                {
                 results=statement.executeQuery("select Office_Level_Id,Office_Level_Name from COM_MST_OFFICE_LEVELS");
                 while(results.next())
                {
                  out.println("<option value='" + results.getString("Office_Level_Id") + "'>" + results.getString("Office_Level_Name") + "</option>");
                  /* int t=results.getInt("District_Code");
                  out.println("<option value='" + t + "'>" + t + "</option>");*/
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
          <td class="table">Registration Class Id</td>
          <td class="table">
           <input type="HIDDEN" value="hclass" id="hclass" name="hclass"/>
            <select name="txtClassId" onblur="check();">
              <option>--Select Here--</option>
              
               <%
              try
                {
                 results=statement.executeQuery("select REGN_CLASS_ID,REGN_CLASS_DESC from PMS_MST_CON_CLASS ORDER BY REGN_CLASS_ID");
                 while(results.next())
                {
                  out.println("<option value='" + results.getString("REGN_CLASS_ID") + "'>" + results.getString("REGN_CLASS_DESC") + "</option>");
                  /* int t=results.getInt("District_Code");
                  out.println("<option value='" + t + "'>" + t + "</option>");*/
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
          <td class="table">Date Effective from</td>
          <td class="table">
            <input type="text" name="txtDate" maxlength="10" size="10"/>
          </td>
        </tr>
        <tr>
          <td colspan="2" class="table">
            <input type="button" name="CmdAdd" value="  ADD  " id="CmdAdd" onclick="addRecord()"/>
            <input type="button" name="CmdDelete" value="REMOVE"
                   id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll()"/>
           <input type="BUTTON" value="  EXIT  " onclick="self.close();"/>
          </td>
        </tr>
      </table>
      <table cellspacing="3" cellpadding="2" border="1" width="75%"
             align="center" >
        <tr>
          <td class="tdH"><B>Existing Details</B></td>
        </tr>
      </table>
     <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="75%"
             align="center">
        <tr class="table">
          <th>
            <FONT face="Times New Roman">Select</FONT>
            </th>
           <th>
             <FONT face="Times New Roman">Sl.No</FONT>
             </th>
          <th>
            <FONT face="Times New Roman">Office Level Id</FONT>
            </th>
          <th>
            <FONT face="Times New Roman">Registration Class Id</FONT>
            </th>
          <th>
            <FONT face="Times New Roman">Date Effective form</FONT>
            </th>
          </tr>
          <tbody id="tblList" class="table">
          
          </tbody>
          
      </table>
      
    </form></body>
</html>


