<%@ page contentType="text/html;charset=windows-1252" session="false"%>
<%@ page import="java.sql.*,java.util.*"%>
<%
  Connection connection=null;
  Statement statement=null;
  Statement statement1=null;
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
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    
    <title>Twad Board Intranet Services</title>
    <link href="css/index.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"     src="org/Security/scripts/twad.js">     </script>
    <link href="css/index.css" rel="stylesheet" media="screen"/>
  <script type="text/javascript" src="org/Events/scripts/IndexPopupOpens.js">
  
    </script>
    <script language="javascript" type="text/javascript">
   history.forward();
    function notNull()
    {
         if((document.frmindex.txtID.value==null)||(document.frmindex.txtID.value.length==0))
        {
            alert("Enter User Name");
            document.frmindex.reset();
           
            return false;
        }
        else  if((document.frmindex.txtPassword.value==null)||(document.frmindex.txtPassword.value.length==0))
        {
            alert("Enter Password");
            document.frmindex.reset();
            
            return false;
        }
        else
        {
             //document.frmindex.action="ServletLogin.con";
             document.frmindex.action="UserValidation.ser";
             document.frmindex.method="POST";
             document.frmindex.submit();
             
        }
        return true;
    }
    
    </script>
  </head>
  <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
  
  <table cellspacing="0" width="100%" height="100%">
    
      <tr >
        <td colspan="3">
            <table cellpadding='0' cellspacing='0' border='0'>
                <tr>
                    <td align='right'><img src="images\twadhead.gif"></img></td>
                    
                </tr>
                <tr>
                    <td  align='right'><img src="images\twadanimat.gif"></img></td>
                </tr>
            </table>
                        
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
            <table cellspacing="0" class="LoginWnd" style="height:100%">
              <tr style="height:50%">
                <td>
                  <div id="headLogin" class="LoginHead">&nbsp;&nbsp;&nbsp;Member
                                                        Login</div>
                  <table cellspacing="0" cellpadding="0" border="0">
                    <tr>
                      <td>
                        <form name="frmindex" method="POST">
                          <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td colspan="2">&nbsp;</td>
                            </tr>
                            <tr>
                              <td>User&nbsp;Name</td>
                              <td>
                                <input type="text" name="txtID" maxlength="15"
                                       size="15"  value="twad11263"></input>
                              </td>
                            </tr>
                            <tr>
                              <td>Password</td>
                              <td>
                                <input type="password" name="txtPassword"
                                       maxlength="15" size="15" value="twad11263"  ></input>
                              </td>
                            </tr>
                            <tr>
                                <td colspan="2">&nbsp;</td>
                            </tr>
                            <tr>
                              <td colspan="2" align="center">
                                <input type="image" onclick="return notNull()"
                                       src="images/Login_but.JPG"
                                       name="butSubmit"
                                       style="border-width:2.0px; border-color:rgb(208,229,233); border-style:groove;"/>
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2" class="forgetPwd"
                                  
                                  align="center" valign="center">
                                <img src="images/index.png" alt="Fogot Password"></img>
                                <a style="text-decorate:none;cursor:hand" onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/org/HR/HR1/EmployeeMaster/jsps/ForgetPasswordJSP.jsp')">Forgot&nbsp;Your&nbsp;Password?</a>
                              </td>
                            </tr>
                          </table>
                        </form>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
          <tr style="height:100%">
            <td >
              <div id=events class="EventWnd" style="height:100%">
                  <div id="eventHead" class="LoginHead">&nbsp;&nbsp;Events</div>             
                  <div id="eventbody"  >                                     
                 
                  <%
                             String my_content="'";
                                                        try
                                                        {
                                                          results=statement.executeQuery("select EVENT_ID,EVENT_DESC from COM_MST_EVENTS where EVENT_DATE >((select now() )-1) and EVENT_STATUS_ID='PD' ORDER BY EVENT_DATE"); 
                                                          while(results.next())
                                                          {
                                                              
                                                              //out.print("<a href=" + results.getInt("District_Code") + ">" + results.getString("District_Name") + "<BR>");                      
                                                               int eve_id=results.getInt("EVENT_ID");
                                                               //my_content+="<a href=indexNewSecond.jsp onclick=fun1("+eve_id+") >"+results.getString("EVENT_NAME") + "<BR>";                      
                                                               my_content=my_content+"<A href=# onclick=openEvtWnd("+eve_id+")>"+results.getString("EVENT_DESC") +"<BR><BR>";
                                                               
                                                               
                
                                                          }
                                                          my_content=my_content+"'";
                                                          System.out.println("content is :"+my_content);
                                                          
                                                        }
                                                        catch(Exception e)
                                                        {
                                                            System.out.println("exception occured : " + e);
                                                        }  
                                                        results.close();
                   %>
                 <input type="hidden" name="content_tot" id="content_tot" value=<%=my_content%>>
                 <table><tr><td height="100%">
                  <DIV align=center>
                    <script type='text/javascript' src="org/Events\scripts\EventsScrolling.js">
                    </script>

                  </DIV>
                  </td></tr></table>
                    
                  </div>
              </div>
            </td>
          </tr>
            </table>
          </div>
        </td>
        <td valign="top">
          <table cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td valign="top">
                <div  id="Center" class="BodyCenter">
                  <hr style="border-width:2.0px; border-style:groove; border-color:rgb(210,225,228);"></hr>
                  <p align="center">
                    <span style="font-size: 14pt; font-family: Arial">
                      Welcome To TWAD Board
                      <br></br>
                      Intranet Services
                    </span>
                  </p><hr style="border-width:2.0px; border-style:groove; border-color:rgb(210,225,228);"></hr>
                   
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
        <td >
          <div id="Right" class="BodyRight" style="height:100%">
            <table cellspacing="0" width="100%" style="height:100%" >
              <tr >
                <td valign="top">
                  <div id="news" class="NewsWnd" style="height:100%">
                    <div id="headNews" class="LoginHead">&nbsp;&nbsp;News</div>
                     
                    <div id="bodyNews" style="height:80%">
                      <%
                                                        try
                                                        {
                                                          System.out.println("inside news");
                                                          results1=statement.executeQuery("select NEWS_ID,NEWS_DESC from COM_MST_NEWS where NEWS_DATE >((select now() )-1) and NEWS_STATUS_ID='PD' ORDER BY NEWS_DATE"); 
                                                         
                                                        for(int i=1;i<10;i++)
                                                          {
                                                               System.out.println("aftr exe");
                                                              results1.next();
                                                              //out.print("<a href=" + results.getInt("District_Code") + ">" + results.getString("District_Name") + "<BR>");                      
                                                               int eve_id=results1.getInt("NEWS_ID");
                                                               out.print("<center><a href='#' onclick='openNwsWnd("+eve_id+")' >"+results1.getString("NEWS_DESC") + "</a></center><BR>");                      
                                                          out.print("");
                                                          }
                                                          results.close();
                                                        }
                                                        catch(Exception e)
                                                        {
                                                            System.out.println("exception occured : There is No published News Items" + e);
                                                        }      
                       %>
                    </div>
                    <div id="seemore" style="height:15%" >
                <a  id="more" name="more" href='#' onclick='opnListOfNews()' class="forgetPwd" style="height:100%"><font color="Black">SeeMore...</font></a>
              
                 </div>
                  </div>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
          Designed and Developed By<br>
              <a href='#' onclick="open_New_Win('www.tn.nic.in')" >National Informatics Centre</a>, Chennai.
             </center>   
          
          </div>
        </td>
      </tr>
    </table></body>
</html>
<%
try
{
   String message=request.getParameter("message"); 
   System.out.println("Message:"+message);
   request.setAttribute("message","");
   if(message!=null)
   {
           if(message.equals("yes"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Invalid User name or Password</label>");    
        out.println("<script language='javascript'>alert('Invalid User name or Password.');</script>");
           }
           else if(message.equals("noprofile"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Your profile has to\nbe updated. Please contact \nSystem Administrator.</label>");    
        out.println("<script language='javascript'>alert('Your profile has to be updated.\\n Please contact System Administrator.');</script>");
           }
           else if(message.equals("dbnill"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Your profile has to\nbe updated. Please contact \nSystem Administrator.</label>");    
        out.println("<script language='javascript'>alert(\"Database Service is not available.\\n Please Contact System Administrator.\");</script>");
           }
    }
}catch(Exception e)
{
                // while loading first
}
%>