<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
  <link href="../../../css/Sample3.css" rel='stylesheet' media='screen'/>
  
  <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>View All Response</title>
    <script type="text/javascript" src="../scripts/ViewAllResponse.js"></script>
    
    <script type="text/javascript"       src="../../../org/Library/scripts/checkDate.js"></script>
  
     <link  href="../../../css/CalendarControl.css" rel="stylesheet"         media="screen"/>
    
      <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
     
     <style type="text/css">
      .tl{
      width: 100em;
      }
    </style>
    
    <script language="javascript" type="text/javascript">
    
    function closeWindow()
    {                
      window.open('','_parent','');                
      window.close(); 
      window.opener.focus();
    }
    </script>
      
  </head>
  <!--onload="callServer()"-->
  <body class="table" >
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
            connection.clearWarnings();
       }
       catch(SQLException e1)
       {
          System.out.println("Exception in creating statement:"+e1.getMessage());
       }          
  }
  catch(Exception e)
  {
        System.out.println("Exception in openeing connection:"+e.getMessage());
  }
  
  %>

  
  <form name="frmIssue"  action="">
  <table  border="1" cellspacing="2"   cellpadding="1"  class="tl"  >
        <tr>
            <td class="tdH"><center><b>View All Response</b></center></td>
        </tr>
        <tr>
        <td>
         <table border="1" cellspacing="2"   cellpadding="1" >
              <tr>
                <td  width="auto" >From&nbsp;Date:</td>
                <td  width="auto">
                  <input type="text" name="txtfromdate" id="txtfromdate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/><img src="../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmIssue.txtfromdate);"
                       alt="Show Calendar" height="24" width="19"/>
                </td>
                <td  width="auto">To&nbsp;Date:</td>
                <td  width="auto">
                  <input type="text" name="txttodate" id="txttodate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/><img src="../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmIssue.txttodate);"
                       alt="Show Calendar" height="24" width="19"/>
                </td>
                <td  width="auto">Status</td>
                <td  width="auto"><select name="cmbstatus" >
                <option value="O">Open</option>
                <option value="C">Closed</option>
                <option value="A">All</option>
                </select>
                </td>
                <td width="auto">Major System</td>
                <td width="auto">
                <select name="cmbMajor" id="cmbMajor">
                  <option value=0>--Select Major System--</option>
                  <%
                     try
                     {
                                        
                       PreparedStatement ps1=connection.prepareStatement("select major_system_id,major_system_desc from sec_mst_major_systems where major_system_id not in ('ASA','HLD')");
                       ResultSet res1=ps1.executeQuery();
                       while(res1.next())
                        {
                           out.println("<option value="+res1.getString("major_system_id")+">"+res1.getString("major_system_desc")+"</option>");
                                           
                        }
                      }
                      catch(Exception e)
                      {
                        System.out.println("Exception in major:"+e);
                      }
                   %>
                  </select>
                  </td>
                        
                
                
                <!--td width="100%">
                &nbsp;
                </td>
              </tr-->
              <tr>
                <td colspan="8" class="tdH" align="left"  width="auto">
                  <input type="button" onclick="buttonsubmit()" value="Submit" />
                <!--  <input type="reset" value="Clear"/>-->
                  <input type="button" value="Exit" onclick="closeWindow()"/>
                </td>
              </tr>
            </table>
        </td>
        </tr>
          <tr>
            <td>
            <div style="overflow:auto">
                <table  border=1 cellspacing="3"  class="tl" cellpadding="1"  id="Existing">
                    <tr>
                        <th>Issue Id</th>
                        
                        <th>Emp. Code</th>
                        <th>Employee Name</th>
                        <th>Office Name</th>
                        
                        <th>Major System Id</th>
                        <th>Subject</th>
                        <th>Reported Date</th>
                        <th>Status</th>
                        <th>Description</th>
                        <th>Solution</th>
                    </tr>
                    <tr>
                        <tbody id="tblList" >
                        </tbody>
                    
                    </tr>
                </table>
                
                </div>
            </td>
         </tr>
    </table>
  
  </form>
  
  </body>
</html>