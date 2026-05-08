<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link href="../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../scripts/ListAllScheduleMaster.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    <title>List of Schedule Master</title>
  </head>
  <body class="table">
  <%
  
  Connection con=null;
  ResultSet rs=null,rs2=null;
  PreparedStatement ps=null,ps2=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
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

           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  %>
  <form action="" name="frmListScheduleMaster">
  <table cellspacing="2" cellpadding="3" border="1" width="100%">
  <tr>
        <td class="tdH"><center>List of Schedule Master</center></td>
  </tr>
  <tr>
    <td colspan="3">Schedule Id:
    
        <select name="cmbScheduleid" id="cmbScheduleid" onchange="callServer('list')">
        <option value="0">--Select Schedule Id--</option>
         <%
                               System.out.println("here");
                               
                            try
                            {
                               
                                    ps=con.prepareStatement("select distinct SCHEDULE_ID from FAS_SCHEDULEMASTER");
                                    rs=ps.executeQuery();
                                    //out.println("<option value="+oid+">"+oname+"</option>");
                                    while(rs.next())
                                    {
                                    
                                    out.println("<option value="+rs.getString("SCHEDULE_ID")+">"+rs.getString("SCHEDULE_ID")+"</option>");
                                    }
                                
                                
                            } 
                            catch(Exception e)
                            {
                            System.out.println("Exception in Schedule..."+e);
                            }
                            finally
                            {
                            rs.close();
                            ps.close();
                            }  
                            %>
        </select>
    </td>
  </tr>
    <tr>
    <td colspan="4">
  <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%">
                          <tr class="tdH">
                          <th>
                            Select
                          </th>
                          <th>
                            ACCOUNT_HEAD_CODE
                          </th>
                          <th>
                            SCHEDULE_ID
                          </th>
                          <!--<th>
                            CR_DR_INDICATOR
                          </th>-->
                          
                          </tr>
                          <tr>
            <tbody id="tblList" name="tblList">
                </tbody>
        </tr>
                          </table>
            </td>
        </tr>
        <tr>
        <td align="center">
        <input type="button" name="Exit" value="Exit" onclick="closeWindow()">
        </td>
        </tr>
    </table>
                        
  </form>
  
  </body>
</html>