<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<%@ page import="java.sql.*,java.util.*"%>
<title>EventDetails</title>
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
<link href="css/Sample3.css" rel="stylesheet" media="screen" />
<script language="javascript">
    function open_New_Win(target_page)
    {
    //alert("called  "+target_page);
    
    var url="http://"+target_page;
    Targ_Window=window.open(url,"Target",status=1,height=50,width=50,resizable="true");
    //Targ_Window.moveTo(100,250);
    
    }
    </script>
</head>
<body>

<br>
<br>
<br>
<br>
<br>
<br>
<div>
<table align="center">
	<tr class="tdTitle">
		<td align="center"><font face="Arial" size="5"> Event In
		Detail </font></td>
	</tr>
</table>
</div>

<table cellspacing="2" cellpadding="3" width="100%" align="center">
	<tr class="tdH">

		<th>Event Description</th>
		<th>Venue</th>
		<th>Date and Time</th>

	</tr>
	<%
                       int val=Integer.parseInt(request.getParameter("eveId"));
                      
                                                        try
                                                        {
                                                          results=statement.executeQuery("select * from COM_MST_EVENTS where EVENT_ID="+val); 
                                                          while(results.next()) 
                                                          {
                                                              
                                                              //out.print("<a href=" + results.getInt("District_Code") + ">" + results.getString("District_Name") + "<BR>");                      
                                                               int eve_id=results.getInt("EVENT_ID");
                                                               String eve_desc=results.getString("EVENT_DESC");
                                                               String target_page=results.getString("TARGET_URL");
                                                               
                                                               out.print("<tr class=table>");                      
                                                               //out.print("<td align=center>"+eve_desc+ "</td>");
                                                               out.print("<td align=center><a href='#' onclick=open_New_Win('"+target_page+"')>"+eve_desc+"</a></td>"); 
                                                               out.print("<td align=center>"+results.getString("EVENT_VENUE") + "</td>");                      
                                                               
                                                                                    
                                                                 
                                                                 java.sql.Date DateOfFormation = results.getDate("EVENT_DATE");  
                                                                 String DateToBeDisplayed="";
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
                                                                 out.print("<td align=center>"+DateToBeDisplayed+ "</td></tr>"); 
                                                               //out.print("<td align=center><a href=# onclick=openNewWin("+target_page+")>"+eve_desc+"</a></td></tr>");
                                                               
                                                          }
                                                          results.close();
                                                        }
                                                        catch(Exception e)
                                                        {
                                                            System.out.println("exception occured : " + e);
                                                        }      
                                                  %>

</table>
</body>
</html>