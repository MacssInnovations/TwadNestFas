<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>



<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Edit Caption Details</title>    
   
    <script type="text/javascript" src="../scripts/EditIconDetailsJS.js"></script>
    
    
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent',''); 
                    window.close(); 
                    var w=window.open(window.location.href,"_self");
                    w.close();
                    window.opener.focus();
                }
                function toExit()
                {
                  //window.close();
                var w=window.open(window.location.href,"_self");
                w.close();
                }
    </script>
    <link href='../../../css/Sample3.css' rel='stylesheet' media='screen'/>
    <link href="../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
  </head>
  <body class="table">
  <%
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null;
   PreparedStatement ps1=null;
   ResultSet rs1=null; 

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
       catch(SQLException e)
       {
              //System.out.println("Exception in creating statement:"+e);
       }          
  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  }  
 %>
  <form name="frmCreateNews" method="POST" action="../../../ServletEditIconDetails.con" onsubmit="return nullCheck()">
      <table width="100%" align="center">
        <tr>
            <td class="tdH">
                <center><b>Edit Caption Details</b></center>
            </td>
        </tr>
        <tr>
            <td>
                <table cellspacing="3" cellpadding="1" width="100%">
                <tr>
                  <td width="36%">
                  News Caption ID                 
                  </td>
                  <td width="64%">       
                    <!--<input type="text" id="txtEventId" name="txtEventId" size="10" onchange="callServer('Existg','null')" onkeypress="return  numbersonly1(event,this)"/>--> 
                    <select name="cmbIcon" id="cmbIcon">
                    <option value>--Select Caption Id--</option>
                    <%
                     try
                     {
                      ps1=connection.prepareStatement("select caption_id,caption from COM_CAPTION_DETAILS where process_flow_status_id='PB' order by caption_id desc");
                      
                      rs1=ps1.executeQuery();
                      
                      while(rs1.next())
                      {
                        out.println("<option value='"+rs1.getInt("caption_id")+"'>"+rs1.getInt("caption_id")+"-"+rs1.getString("caption")+"</option>");
                      }
                      //rs1.close();
                      }
                      catch(Exception e)
                      {
                        System.out.println(e.getMessage());
                      }
                    %>
                    </select>
                  </td>
                </tr>
                
                
                
                <tr>
                  <td width="36%">
                  Icon<label style="color:rgb(255,0,0);">&nbsp;*</label>                 
                  </td>
                  <td width="64%">
                  <input type="radio" name="rd_ico" id="rad_ico" value="Y" checked="checked">&nbsp;Yes&nbsp;&nbsp;
                  <input type="radio" name="rd_ico" id="rad_ico" value="N">&nbsp;No&nbsp;&nbsp;
                  
                  </td>
                </tr>
                
                
                
                
              </table>
          </td>
        </tr>        
        <tr>
        <td align="center" class="tdH">
            <input type="submit" value="Submit" name=submit>
            <input type="button" value="Cancel" onclick="closeWindow()">
        </td>
        </tr>
      </table>
      <p>
        &nbsp;
      </p>
    </form>
  </body>
</html>