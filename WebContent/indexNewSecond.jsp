<%@ page contentType="text/html;charset=windows-1252" session="false"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    <%@ page import="java.sql.*,java.util.*"%>
    <title>Twad Board Intranet Services</title>
    <link href="css/index.css" rel="stylesheet" media="screen"/>
    <script language="javascript">
    function fun1(eve_id)
    {
    //alert("called  "+eve_id);
    var eveId=eve_id;
    var url="EventDetails.jsp?eveId="+eveId;
    DetailsWindow=window.open(url,"Details",status=1,height=50,width=50);
   
    DetailsWindow.moveTo(100,250);
    
    }
    </script>
  </head>
  <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"><table cellspacing="0"
                                                                             border="0">
      <tr valign="top">
        <td colspan="3">
          <img src="images\TWAD3gif.gif" height="80px" width="350px"></img>
        </td>
      </tr>
      <tr>
        <td colspan="3">
          <div id="seperator" class="HeaderSeperator1"></div>
        </td>
      </tr>
      <tr>
        <td>
          <div id="left" class="BodyLeft">
            <table cellspacing="0" width="100%">
              <tr>
                <td>
                  <div id="login" class="LoginWnd">
                    <div id="headLogin" class="LoginHead">&nbsp;&nbsp;&nbsp;Member
                                                          Login</div>
                     
                    <div id="bodyLogin">
                      <!--                   
                  login code                  
                  -->
                       
                      <table cellspacing="0">
                        <tr>
                          <td>
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
                            <form action="ServletLogin.con" method="POST">
                              <pre><b>
<%
try
{
   String message=request.getParameter("message");        
   if(message.equals("yes"))
   {            
out.println("<label style='color:rgb(255,0,0);'>Invalid User name or Password</label>");    
   }
}catch(Exception e)
{
                // while loading first
}
%>
  User Name   
     <input type="text" name="txtID" maxlength="15" size="15" value="twad9972"></input>
  Password   
     <input type="password" name="txtPassword" maxlength="15" size="15"
            value="twad9972"></input>
               
          <input type="image" src="images/Login_but.JPG" name="butSubmit"
                 style="border-width:2.0px; border-color:rgb(208,229,233); border-style:groove;"/>
</b></pre>
                            </form>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <!-- code to forget password
              -->
                            <div id="forgetPwd" class="forgetPwd">
                              <!--  <a href="ForgetPassword.jsp"> -->
                               
                              <div onmouseover="this.style.cursor='hand'"
                                   align="center" valign="center">
                                <img src="images/help.png"></img>
                                 Forgot Your Password?
                              </div>
                               
                              <!--  </a>  -->
                            </div>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </div>
                </td>
              </tr>
              <!--<tr>
              <td>
              <div id="leftspace1" class="BodySpace"></div>
              </td>
          </tr>-->
              <tr>
                <td>
                  <div id="events" class="EventWnd">
                    <div id="eventHead" class="LoginHead">&nbsp;&nbsp;Events</div>
                     
                    <div id="eventbody">
                      <!-- 
                  
                  event body
                  
                  -->
                       
                      <%
                                                        try
                                                        {
                                                          results=statement.executeQuery("select * from events_list where event_date >(select now() )"); 
                                                          for(int i=0;i<=7;i++) 
                                                          {
                                                              results.next();
                                                              //out.print("<a href=" + results.getInt("District_Code") + ">" + results.getString("District_Name") + "<BR>");                      
                                                               int eve_id=results.getInt("EVENT_ID");
                                                               out.print("<a href=indexNewSecond.jsp onclick=fun1("+eve_id+") >"+results.getString("EVENT_NAME") + "<BR>");                      
                                                          }
                                                          results.close();
                                                        }
                                                        catch(Exception e)
                                                        {
                                                            System.out.println("exception occured : " + e);
                                                        }      
                                                  %>
                    </div>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </td>
        <td>
          <table cellspacing="0" width="100%">
            <tr>
              <td>
                <div id="Center" class="BodyCenter">
                  <br></br><br></br>
                   
                  <hr style="border-width:2.0px; border-style:groove; border-color:rgb(210,225,228);"></hr>
                   
                  <center>
                    <span style="font-size: 14pt; font-family: Arial">
                      Welcome To TWAD Board
                      <br></br>
                      Intranet Services
                    </span>
                  </center>
                   
                  <hr style="border-width:2.0px; border-style:groove; border-color:rgb(210,225,228);"></hr>
                   
                  <p align="justify">
                    <span>&nbsp;&nbsp;&nbsp;The Intranet system for TWAD Board
                          facilitates centralized data storage and retrieval
                          besides information analysis pertaining to Project
                          Monitoring, Financial Accounting and HR Management.
                          This system will be made available to all the offices
                          of TWAD Board through a network connecting the Head
                          Office with all the Regions, Circles and Divisions.</span>
                  </p>
                   
                  <hr style="border-width:2.0px; border-style:groove; border-color:rgb(210,225,228);"></hr>
                </div>
              </td>
            </tr>
          </table>
        </td>
        <td>
          <div id="Right" class="BodyRight">
            <table cellspacing="0" width="100%">
              <tr>
                <td>
                  <div id="news" class="NewsWnd">
                    <div id="headNews" class="LoginHead">&nbsp;&nbsp;News</div>
                     
                    <div id="bodyNews">
                      <!-- 
                   News goes here
                  -->
                       News will be listed here
                    </div>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
      <tr>
        <td colspan="3">
          <div id="bottom" class="HeaderSeperator">
            <center>
              Designed and Developed By
              <br></br>
              National Informatics Centre, Chennai.
            </center>
          </div>
        </td>
      </tr>
    </table></body>
</html>