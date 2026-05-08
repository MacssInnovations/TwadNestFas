<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>Schedule Master System</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" type="text/javascript" src="../scripts/ScheduleMaster.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body class="table">
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
                   
                // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
  <form action="../../../../../ScheduleID_Master.kv" name="frmScheduleMaster" method="POST">
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Schedule Master Details</b></td>
                   
       </tr> 
        <tr>
                <td>Account Head Code:<font color="#ff2121">*</font></td>
                <td>
                    <input type=text name=txtaccountheadcode size=8 maxlength=8 onchange="doFunction('checkCode','null')" onkeypress="return numbersonly1(event,this)">
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                 height="20" alt="AccountHeadList"
                 onclick="AccHeadpopup();"></img>
                </td>
        </tr>
        <tr>
                <td>Account Head Name:</td>
                <td><input type=text name="txtaccountheadname" id="txtaccountheadname"  size="55" disabled >
        </tr>
        <tr>
                <td>Schedule Id</td>
                <td><input type="text" name="txtScheduleId" id="txtScheduleId" size="25" maxlength="4" style="TEXT-TRANSFORM:UPPERCASE" onkeypress="return key(event,this)">
        </tr>
        <!--<tr>
            <td>CR/DR Indicator</td>
            <td><input type=radio name="radCrDrInd" value="CR" checked>CR
            <input type="radio" name="radCrDrInd" value="DR">DR
            </td>
        </tr>-->
        <tr>
                <td colspan="2" align="left">
                <table>
                <tr>
                <td>
                <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')"/>
                </td>
                <td>
                <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="doFunction('Update','null')"/>
                </td>
                <td>
                <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="doFunction('Delete','null')" />
                </td>
                <td>
                <input type="reset" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()"/>
                </td>
                
                <td>
                <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="ListAllBudget()"/>
                </td>
                
                <td>
                <input type="submit" name="cmdListAll" value="LISTALL" id="cmdListAll" />
                </td>
                                
                <td>
                <input type="button" id="Exit" name="Exit" value="EXIT" onclick="closeWindow()">
                </td>
                </tr>
                </table>
               </td>
         </tr>
    </table>
  </form>  
  </body>
</html>