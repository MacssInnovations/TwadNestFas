<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <script type="text/javascript" src="../scripts/AcHead_SubGrpScript.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <title>Account Head Sub Group Master</title>
  </head>
  <body  bgcolor="rgb(255,255,225)">
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

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
  <form name="AcHead_SubGroupForm" id="AcHead_SubGroupForm">
  
      <table cellspacing="3" cellpadding="2" border="1" width="75%" align="center">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              Account Head Classification(Sub Group)System
            </div></td>
        </tr>
        <tr class="table">
          <td width="39%">Sub Group Code</td>
          <td width="61%">
            <input type="text" name="txtSubGrpCode" maxlength="3" size="5"
                   id="txtSubGrpCode" class="disab" readonly/>
          </td>
        </tr>
        <tr class="table">
          <td width="39%">Sub Group Description</td>
          <td width="61%">
            <input type="text" name="txtSubGrpDesc" maxlength="30" size="30"
                   id="txtSubGrpDesc"/>
          </td>
        </tr>
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
            <table><tr><td>
              <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="callServer('Add','null')"/>
              </td><td>
              <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" onclick="callServer('Update','null')" style="display:none"/>
              </td><td>
              <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" onclick="callServer('Delete','null')" style="display:none"/>
              </td><td>
              <input type="button" name="cmdCancel" value="EXIT" id="cmdCancel" onclick="Exit()"/>
              </td><td>
              <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="clearAll()"/>
              </td></tr></table>
            </div></td>
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
            Sub Group Code
          </th>
          <th>
            Sub Group Description
          </th>
          </tr>
          <tbody id="tblList" class="table">
           <%
      
          try
          {
              
            String sql="select * from COM_MST_SUB_HEADS order by SUB_HEAD_CODE";
             results=statement.executeQuery(sql);
            try
            {
              while(results.next())
              {
                String strSubCode=results.getString("SUB_HEAD_CODE");
	        String strSubDesc=results.getString("SUB_HEAD_DESC");
                out.println("<tr id='" + strSubCode + "'>");   
                out.println("<td><a href=\"javascript:loadValuesFromTable('" + strSubCode + "')\">Edit</a></td>");
                out.println("<td>" + strSubCode + "</td>");
	 out.println("<td>" + strSubDesc + "</td></tr>");
                 
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