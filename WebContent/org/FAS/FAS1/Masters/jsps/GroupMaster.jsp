<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Group Master</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/GroupMaster.js"></script>
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
  <body onload="callServer('Get','null');callServer('Asset','null')" class="table">
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
    
                //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
    				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                 Class.forName(strDriver.trim());
                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
      }
      catch(Exception e)
      {
        System.out.println("Exception in connection...."+e);
      }
  %>
  
  
  <form action="" name="frmGroupMaster" id="frmGroupMaster">
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Group Master</b></td>
                   
       </tr> 
        <tr>
            <td class="table">Group Id</td>
          <td class="table">
            <input type="text" name="txtGroupId" maxlength="2"
                   id="txtassetclasscode" readonly size="3"/>System Generated
           </td>
           
        </tr>
        <tr class="table">
                <td>
                  <div align="left">
                     Section Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                 
                <td>
                  <div align="left">
                
                    <select size="1" name="cmbsection" id="cmbsection" tabindex="3" >
                    <option>----Select Section----</option>
                      <%
                    try
                    {
                     ps=con.prepareStatement("select SECTION_ID,SECTION_NAME from FAS_MST_SECTIONS ");
                     // ps.setInt(1,oid);
                     rs=ps.executeQuery();
                     while(rs.next())
                     {
                        out.println("<option value="+rs.getInt("SECTION_ID")+">"+rs.getString("SECTION_NAME")+"</option>");
                     }
                      System.out.println();  
                    } 
                    catch(Exception e)
                    {
                    System.out.println("Exception in bank combo..."+e);
                    }
                    finally
                    {
                    rs.close();
                    ps.close();
                    }  
                %>
                    </select>
                  </div>
                </td>
              </tr>
        <tr>
            <td class="table">Group Name</td>
            <td class="table">
            <input type="text" name="txtGroupName" size="100" maxlength="50"
                   id="txtGroupName"/>
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
            Group Id
          </th>
           <th>
          Section Name
          </th>
          <th style="display:none"> Section Id </th> <!---->
           <th>
            Group Name
          </th>
          
        </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
        
    
  </form>
  
  </body>
</html>