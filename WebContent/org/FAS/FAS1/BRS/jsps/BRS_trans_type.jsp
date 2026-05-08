<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>BRS Transaction Type</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/BRS_trans_type.js"></script>
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
  
  
  <form action="" name="frmBRS_trans">
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>BRS Transaction Type</b></td>
                   
       </tr> 
        <tr>
            <td class="table">BRS Transaction Code</td>
          <td class="table">
            <input type="text" name="txtbrscode" maxlength="2"
                   id="txtjournaltypecode" readonly size="3"/>System Generated
           </td>
           
        </tr>
        <tr>
            <td class="table">BRS transaction short Description</td>
            <td class="table">
            <input type="text" name="txtbrs_shdesc" size="30" maxlength="25"
                   id="txtbrsdesc"/>
        </tr>
        <tr>
            <td class="table">BRS Description</td>
            <td class="table">
            <input type="text" name="txtbrsdesc" size="30" maxlength="25"
                   id="txtbrsdesc"/>
        </tr>
        
        <tr>
            <td class="table">BRS Transaction Type</td>
            <td class="table">
            <input type="text" name="txtbrstype" size="30" maxlength="25"
                   id="txtbrstype"/>
        </tr>
        <tr class="table">
                <td>
                  <div align="left">
                            Twad <font color="#ff2121">*</font>
                          </div>
                </td>
                <td>
                  <div align="left">
                           NonTwad <font color="#ff2121">*</font>
                          </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="Indi_T_NT" id="Indi_T_NT" 
                           checked="checked" value="T"/>Twad &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="Indi_T_NT" id="Indi_T_NT" 
                           value="NT"/>NonTwad  &nbsp;&nbsp;&nbsp;&nbsp;    
                  </div>
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
            BRS transaction Code
          </th>
          <th>
             BRS Short Description
          </th>
          
          <th>
             BRS Description
          </th>
          <th>
             BRS Transaction Type
          </th>
        </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
    
  </form>
  
  </body>
</html>