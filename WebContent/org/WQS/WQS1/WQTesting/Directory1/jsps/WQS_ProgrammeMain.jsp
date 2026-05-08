<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="../scripts/ProgrammeMain.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    
    <title>Master WQS Programme Details</title>
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
    }
    catch(SQLException e)
    {
    }
  }
  catch(Exception e)
  {
  }
  %>
  <form name="ProgForm" id="ProgForm">
      <table cellspacing="2" cellpadding="3" border="1" width="75%"
             align="center">
        <tr>
          <td colspan="2" class="tdH">
            <div align="center">
              <strong>Programme Main Details</strong>
            </div></td>
        </tr>
        <tr>
          <td class="table">Programme Code :</td>
          <td class="table">
           
                   <input type="TEXT" name="wtxtProgId" maxlength="3"
                   id="wtxtProgId" size="5"/>
          </td>
        </tr>
        <tr>
          <td class="table">Description :</td>
          <td class="table">
            <input type="text" name="txtProgDesc" size="40" maxlength="60"
                   id="txtProgDesc"/>
          </td>
        </tr>
        <tr>
          <td colspan="2" class="table" id="add">
            <input type="button" name="CmdAdd" value=" ADD " id="CmdAdd" onclick="callServer('Add','null')"/>
            
           
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdDelete" value="DELETE"
                   id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
                   <input type="button" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll()"/>
                   <input type="button" name="CmdExit" value="EXIT"
                   id="CmdExit" onclick="Exit()"/>
                   
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
            Programme Code
        </th>
          <th>
            Programme Description
          </th>
          </tr>
          <tbody id="tblList" class="table">
           <%
      
          try
          {
              
            String sql="select * from WQS_MST_PROGRAMME_MAIN_TYPES ORDER BY PROGRAMME_MAIN_TYPE_ID";
             results=statement.executeQuery(sql);
            try
            {
              while(results.next())
              {
                String strCusId=results.getString("PROGRAMME_MAIN_TYPE_ID");
	        String strCusDesc=results.getString("PROGRAMME_MAIN_TYPE_DESC");
                if(strCusDesc==null)
                                       {
                                           strCusDesc="Undefined Record Found";
                                           
                                       }
                                       else {
                                           strCusDesc=strCusDesc;
                                           
                                       }
                out.println("<tr id='" + strCusId + "'>");   
                out.println("<td><a href=\"javascript:loadValuesFromTable('" + strCusId + "')\">Edit</a></td>");
                out.println("<td>" + strCusId + "</td>");
	 out.println("<td>" + strCusDesc + "</td></tr>");
                 
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
      
     
    </form></body>
</html>