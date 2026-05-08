<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>



<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Delete Caption Details</title>    
   
    <script type="text/javascript" src="../scripts/DeleteCaptionDetailsJS.js"></script>
    
    
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
  <form name="frmCreateNews" method="POST" action="../../../ServletDeleteCaptionDetails.con" onsubmit="return nullCheck()">
      <table width="100%" align="center">
        <tr>
            <td class="tdH">
                <center><b>Delete Caption Details</b></center>
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
                    <input type="text" id="txtEventId" name="txtEventId" size="10" onchange="callServer('Existg','null')" onkeypress="return  numbersonly1(event,this)"/>                       
                  </td>
                </tr>
                
                <tr>
                  <td width="36%">
                  News Caption<label style="color:rgb(255,0,0);">&nbsp;*</label>                 
                  </td>
                  <td width="64%">
                  <input type="text" id="txtCaption" name="txtCaption" size="40" onkeypress="return noEnter(event)"/>
                  </td>
                </tr>
                
                
                <tr>
                  <td width="36%">
                  Brief Description<label style="color:rgb(255,0,0);">&nbsp;*</label>                   
                  </td>
                  <td width="64%">
                  <textarea name="txtDesc" cols="30" rows="7" onkeypress="return noEnter(event)"></textarea>
                  <%
               
                  String DateToBeDisplayed="";
                    try
                           {
                                 results=statement.executeQuery("select now() from dual"); 
                                 while(results.next())
                                 {
                                   java.sql.Date DateOfFormation = results.getDate(1);  
                                    
                                     if(DateOfFormation==null)
                                     {
                                         DateToBeDisplayed="Not Specified";
                                     }
                                     else
                                     {
                                         try
                                         {
                                             java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                                             DateToBeDisplayed=sdf.format(DateOfFormation);
                                         }
                                         catch(Exception e)
                                         {
                                             System.out.println("error while formatting date : " + e);
                                             DateToBeDisplayed="Not Specified";
                                         }
                                     }     
                                 //out.print("<a href=" + results.getInt("District_Code") + ">" + results.getString("District_Name") + "<BR>");                      
                                  //eve_date=results.getDate(1);
                                  System.out.println("the value is "+DateToBeDisplayed); 
                                  
                                 }
                                results.close();
                           }
                    catch(Exception e)
                           {
                                System.out.println("exception occured : " + e);
                           }    
                    
                    %>
                    
                    <input type="hidden" id="txtEventDate_h" name="txtEventDate_h" value=<%=DateToBeDisplayed%>></input>
                  </td>
                </tr>
                
                <!--<tr>
                    <td  width="36%">Attach File</td>
                    <td width="64%">
                  <input type="file" id="txtattachFile" name="txtattachFile" size="40" />
                  &nbsp;
                </td>
                </tr>-->
              </table>
          </td>
        </tr>        
        <tr>
        <td align="center" class="tdH">
            <input type="submit" value="Delete" name=submit>
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