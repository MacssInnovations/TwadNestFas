<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Customer Sub Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/CustomerSub.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
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
    
  <form action="" name="CusForm">
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Customer Sub Details</b></td>
                   
       </tr> 
        <tr>
            <td class="table">Customer Main Type Id</td>
          <td class="table">
                    <select id="txtMainId" name="txtMainId" maxlength="5">
           <%
      
          try
          {
              
            String sql="select * from WQS_MST_CUSTOMER_MAIN_TYPES ORDER BY CUSTOMER_MAIN_TYPE_ID";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strCusId=results.getString("CUSTOMER_MAIN_TYPE_ID");
	        //String strCusDesc=results.getString("CUSTOMER_TYPE_DESC");
               
                out.println("<option value='" + strCusId + "'>"+strCusId+"</option>");   
                //out.println("<td><a href=\"javascript:loadValuesFromTable('" + strCusId + "')\">Edit</a></td>");
                //out.println("<td>" + strCusId + "</td>");
	// out.println("<td>" + strCusDesc + "</td></tr>");
                 
              }
            }
            catch(Exception e)
            {
              System.out.println("Exception in resultset:"+e);
            }
  %>   
            
            
            </select>
           </td>
         
           
           
        </tr>
        <tr>
            <td class="table">Customer Sub Type Id</td>
            <td class="table">
            <input type="text" name="txtSubId" size="4" 
                   id="txtSubId"/>
            </td>
        </tr>
        
        <tr>
            <td class="table">Customer Sub Type Desc</td>
            <td class="table">
            <input type="text" name="txtDesc" size="50"
        id="txtDesc"/>
            </td>
        </tr>      
              
        <tr>
          <td colspan="2" class="table">
            <input type="button" name="CmdAdd" value=" ADD " id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdDelete" value="DELETE"
                   id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll()"/>
            <input type="button" name="Exit" value="EXIT" onclick="closeWindow()">
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
            Customer Main Type Id
          </th>
         <th>
            Customer Sub Type Id
          </th>
         
          <th>
            Customer Sub Type Description
          </th>
          </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
    
  </form>
  
  </body>
</html>