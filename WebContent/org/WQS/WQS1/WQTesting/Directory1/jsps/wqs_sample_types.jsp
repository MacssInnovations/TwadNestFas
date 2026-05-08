<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>ProjectMonitoringSample</title>
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
    <script language="javascript" src="../scripts/wqs_sample_types.js" type="text/javascript">
    </script>
  </head>
<body class="table">
<%
        Connection con=null;
        Statement stat=null;
        ResultSet rs,r=null;
        PreparedStatement ps=null;
        
        
        try
        {
                        ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                       String ConnectionString="";

                       String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                       String strdsn=rs1.getString("Config.DSN");
                       String strhostname=rs1.getString("Config.HOST_NAME");
                       String strportno=rs1.getString("Config.PORT_NUMBER");
                       String strsid=rs1.getString("Config.SID");
                       String strdbusername=rs1.getString("Config.USER_NAME");
                       String strdbpassword=rs1.getString("Config.PASSWORD");
                       ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                       //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                       Class.forName(strDriver.trim());
                       con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                       try
                       {
                            stat=con.createStatement();
                            con.clearWarnings();
                       }
                       catch(SQLException e)
                       {
                            System.out.println("Exception in creating statement:"+e);
                       }
         }
          catch(Exception e)
          {
             System.out.println("Exception in opening connection:"+e);
          }



%>

<form name=frmSamType>

<table cellspacing="2" cellpadding="3" border="1" width="100%" align="center" class="table">
  <tr class="tdH">
    <td colspan="2" align="center">
        Sample Type
    </td>
  </tr>
   <tr class="table">
      <td width="40%">
        Sample Id<font color="Red">*</font>
      </td>
      <td width="60%">
        <input type="text" name="txtSamId" id="txtSamId" disabled="disabled" style="background-color:rgb(214,214,214)">
      </td>
  </tr>
  
  <tr class="table">
      <td width="40%">
        Sample Type<font color="Red">*</font>
      </td>
      <td width="60%">
        <input type="text" name="txtSamTyp" id="txtSamTyp"/>
      </td>
  </tr>
<tr class="table">
          <td colspan="2" align="center" height="36">
            <input type="button" name="CmdAdd" value="Add" id="CmdAdd" onclick="callServer('Add')">
            <input type="button" name="CmdUpdate" value="Update" id="CmdUpdate" onclick="callServer('Update')" disabled="disabled">
            <input type="button" name="CmdDelete" value="Delete" id="CmdDelete" onclick="callServer('Delete')" disabled="disabled">
            <input type="button" value=" Clear" id="CmdClear" onclick="clearAll()"/>
          </td>
        </tr>
        <tr class="table">
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="exit" value="  Exit  " id="exit" onclick="javascript:self.close();"/>
          </td>
</tr>
</table>
<table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center" class="table">
<tr class="tdTitle"><td colspan="17">Existing Details</td></tr>
 <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Sample Type Id
          </th>
          <th>
            Sample Type
          </th>
          </tr>
          <tbody id="tblList" class="table">
           <%
          try
          {
             String sql="select sample_id,sample_type from wqs_sample_type";
             rs=stat.executeQuery(sql);
            try
            {
              while(rs.next())
              {
                String strSamId=rs.getString("sample_id");
	        String strSamTyp=rs.getString("sample_type");
                out.println("<tr id='" + strSamId + "'>");   
                out.println("<td><a href=\"javascript:loadValuesFromTable('" + strSamId + "')\">Edit</a></td>");
                out.println("<td>" + strSamId + "</td>");
                out.println("<td>" + strSamTyp + "</td></tr>");
              }
            }
            catch(SQLException e)
            {
              System.out.println("Exception in resultset:"+e);
            }
            finally
            {
              rs.close();
            }
          }
      catch(SQLException e)
      { 
        System.out.println("Exception :"+e);
      }
    %>
          </tbody>
      </table>
</form>
</body>
</html>