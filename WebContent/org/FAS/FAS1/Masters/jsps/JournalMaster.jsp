<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Master Journal Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/JournalMaster.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <!--onload="callServer('Get','null')"-->
  <body onload="callServer('Get','null')" class="table">
  <%
  Connection connection=null;
  Statement statement=null;
  ResultSet results=null;
  ResultSet results1=null;
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
                   
               //  ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
					ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
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
  
  
  <form action="" name="frmJournalMaster">
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Journal Details</b></td>
                   
       </tr> 
        <tr>
            <td class="table">Journal Type Code</td>
          <td class="table">
            <input type="text" name="txtjournaltypecode" maxlength="2"
                   id="txtjournaltypecode" readonly size="3"/>System Generated
           </td>
           
        </tr>
        <tr>
            <td class="table">Category</td>
            <td class="table">
            <select name="cmbCategory" id="cmbCategory" onchange="callServer('Load','null')">
            <option value="0">--Select Category--</option>
            <option value="G">G</option>
            <option value="L">L</option>
            </select>
        </tr>
        <tr>
            <td class="table">Journal Description</td>
            <td class="table">
            <input type="text" name="txtjournaltypedesc" size="75" maxlength="75"
                   id="txtjournaltypedesc" style="width: 569px; "/>
        </tr>
        <tr>
            <td class="table">Display Restricted</td>
            <td class="table">
            <input type="radio" name="displayRes" size="30" maxlength="25"   id="displayRes" value="Y" checked/>Yes
            <input type="radio" name="displayRes" size="30" maxlength="25"   id="displayRes" value="N"/>No
        </tr>
        
        <tr>
            <td class="table">Remarks</td>
            <td class="table">
                   <textarea name="remark" id="remark" cols="50" tabindex="8" 
                              rows="4"></textarea>
        </tr>
        <tr>
            <td class="table">Usage Restricted</td>
            <td class="table">
            <input type="radio" name="usageRes" size="30" maxlength="25"   id="usageRes" value="Y" checked/>Yes
            <input type="radio" name="usageRes" size="30" maxlength="25"   id="usageRes" value="N"/>No
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
            <input type="button" name="Exit" value="Exit" onclick="closeWindow()">
          </td>
        </tr>
    </table>
    <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center" >
        <tr>
          <td class="table"><b>Existing Details</b></td>
        </tr>
      </table>
      <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Journal Type Code
          </th>
          <th>
            Journal Type Description
          </th>
          <th>
            Category
          </th>
          <th>
            Display Restricted
          </th>
          <th>
            Remarks
          </th>
          
          <th>
            Usage Restricted
          </th>
          
          
        </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
    
  </form>
  
  </body>
</html>