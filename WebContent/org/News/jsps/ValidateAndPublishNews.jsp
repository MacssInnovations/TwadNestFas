

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>



<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Validate/Publish News</title>    
   
    <script type="text/javascript" src="../scripts/ValidateAndPublishNewsValids.js"></script>
    <script type="text/javascript" src="../scripts/ValidateAndPublishNewsAjax.js"></script>
    <script type="text/javascript" src="../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../Library/scripts/CalendarControl.js"></script>
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
  <form name="frmPublishNews" method="POST" action="../../../ServletValidateAndPublishNews.con" onsubmit="return nullCheck()">
      <table width="100%" align="center">
        <tr>
            <td class="tdH">
                <center><b>Validate/Publish News</b></center>
            </td>
        </tr>
        <tr>
            <td>
                <table cellspacing="3" cellpadding="1" width="100%" >
                <tr colspan=3>
                  <td width="36%">
                  News ID<label style="color:rgb(255,0,0);">&nbsp;*</label>
                 
                  </td>
                  <td width="64%">
                  <input type="text" id="txtEventId" onchange="editEventDet(this.value)" name="txtEventId" size="10" /> 
                  ( Enter the News Id to be Edited )
                  </td>
                 
                </tr>
                <tr>
                  <td  width="36%">News Description <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                  <td width="64%">
                    <input type="text" id="txtEventDesc" name="txtEventDesc"  size="40"/>                    
                  </td>
                </tr>
         <!--       <tr>
                    <td  width="36%">Event Venue <label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                    <td width="64%">
                        <textarea id="txtEventVenue" name="txtEventVenue" cols="25" rows="4"></textarea>
                    </td>
                </tr>
                
                <tr>
                  <td  width="36%">
                    Date of News  
                  <label style="color:rgb(255,0,0);">
                    &nbsp;*
                  </label>
                </td>
                  <td width="64%">
                   
                           <input type="text" id="txtEventDate" name="txtEventDate" size="10" maxlength="10"
                           onFocus="javascript:vDateType='3'" onBlur="DateFormat(this,this.value,event,true,'3');" onkeypress="return  calins(event,this)"/>                   
                           <img src="../../../images/calendr3.gif"  onclick="showCalendarControl(document.frmPublishNews.txtEventDate);" alt="Show Calendar"></img>
                    
                  </td>
                </tr>
          -->      <tr>
                    <td  width="36%">Target URL<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                    <td width="64%">
                  <input type="text" id="txtTargetURL" name="txtTargetURL" size="40"/>
                </td>
                </tr>
                <tr>
                <td  width="36%">News Status<label style="color:rgb(255,0,0);">&nbsp;*</label></td>
                <td width="64%">
                <select name="cmbStatus" id="cmbStatus" onfocus="return officeCheck()">                                        
                <option value=0>----Select Status------</option>                                            
                        <%
                         try
                          {
                           results=statement.executeQuery("select * from COM_MST_EVENT_STATUS"); 
                           while(results.next()) 
                               {
                                  out.print("<option value='" + results.getString("EVENT_STATUS_ID") + "'>" + results.getString("EVENT_STATUS_DESC") + "</option>");                      
                               }
                           results.close();
                          }
                          catch(Exception e)
                          {
                                System.out.println("exception occured : " + e);
                          }  
                        %>                     
                </select>

                </td>
                </tr>
              </table>
          </td>
        </tr>        
        <tr>
        <td align="center" class="tdH">
            <input type="submit" value="Submit" name=submit >
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