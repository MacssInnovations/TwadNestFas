<%@ page contentType="text/html;charset=windows-1252" session="false" %>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
  
   
    <title>Twad Board Intranet Services</title>
    <link href="css/index.css" rel="stylesheet" media="screen"/>
  <script language="javascript">
  
   function open_New_Win(target_page)
    {
        //alert("called  "+target_page);
        var url="http://"+target_page;
        Targ_Window1=window.open(url,"Target",status=1,height=50,width=50);
    }
    function openEvtWnd(eve_id)
    {
        //alert("called  "+eve_id);
        var eveId=eve_id;
        var url="EventDetails.jsp?eveId="+eveId;
        DetailsWindow=window.open(url,"Details",status=1,height=50,width=50);
   }
    function openNwsWnd(eve_id)
    {
        //alert("called  "+eve_id);
        var eveId=eve_id;
        var url="NewsDetails.jsp?eveId="+eveId;
        DetailsWindow=window.open(url,"Details",status=1,height=50,width=50);
    }
    function moreNews(val)
    {
    //alert("called :"+val);
    if(val=="more")
    {
    
    document.getElementById("bodyNews1").style.visibility="visible";
    document.getElementById("bodyNews1").value="Hide";
    document.getElementById("seemore").style.visibility="hidden";
    }
    else
    {
    document.getElementById("bodyNews").style.visibility="visible";
    document.getElementById("bodyNews").value="Hide";
    document.getElementById("bodyNews1").style.visibility="hidden";
     document.getElementById("seemore").style.visibility="visible";
    }
    }
    </script>
  </head>
  <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
  <table cellspacing="0" border="0">
  <tr valign="top">
    <td colspan=3  >        
          <img src="images\TWADlatest.gif" height="80px" width="350px"></img>        
    </td>
  </tr>
  <tr>
    <td colspan=3>
      <div id=seperator class="HeaderSeperator1">
  
      </div>
   </td>
  </tr>
  <tr>
    <td>
       <div id="left" class="BodyLeft">
          <table cellspacing="0" width="100%">
          <tr>
            <td>
              <div id=login class="LoginWnd">
                  <div id="headLogin" class="LoginHead">&nbsp;&nbsp;&nbsp;Member Login</div>             
                  <div id="bodyLogin" > 
                  <!--                   
                  login code                  
                  -->
                  <table cellspacing="0">
                  <tr>
                  <td>
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
     <input type=text name=txtID maxlength="15" size="15" >
  Password   
     <input type=password name=txtPassword maxlength="15" size="15" >
               
          <input type="image" src="images/Login_but.JPG" name="butSubmit" style="border-width:2.0px; border-color:rgb(208,229,233); border-style:groove;"/>
</b></pre>
              </form>
              </td>              
              </tr>
              <tr>
              <td>
              <!-- code to forget password
              -->
              <div id="forgetPwd" class="forgetPwd" >
                
              <!--  <a href="ForgetPassword.jsp"> -->
                <div onmouseover="this.style.cursor='hand'" align="center" valign="center">
                <img src="images/help.png"> Forgot Your Password?
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
              <div id=events class="EventWnd">
                  <div id="eventHead" class="LoginHead">&nbsp;&nbsp;Events</div>             
                  <div id="eventbody" >                                     
                 
                  <%
                             String my_content="'";
                                                        try
                                                        {
                                                          results=statement.executeQuery("select * from COM_MST_EVENTS where EVENT_DATE >(select now() ) and EVENT_STATUS_ID='PD' ORDER BY EVENT_DATE"); 
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
                  <DIV align=center>
                  
                  <SCRIPT language=JavaScript1.2>
                        var marqueewidth="150px"
                        var marqueeheight="120px"
                        var marqueespeed=1
                        var pauseit=1
                        var marqueecontent='<font face=Arial color=MAROON size=2>'
                     var content_id=document.getElementById("content_tot")
                     //alert(content_id);
                     //alert(content_id.value);
                     marqueecontent=content_id.value;
                     //  marqueecontent=marqueecontent+document.getElementById("content_tot");
                     //alert("vals  :"+document.getElementById("content_tot").value);
                      //  marqueecontent=marqueecontent +'<A href="http://www.annauniv.edu/tnea2006_new.doc" Style="text-decoration:none">Event1<BR><BR>'
                      //  marqueecontent=marqueecontent +'<A href="http://www.annauniv.edu/tnea2006_new.doc" Style="text-decoration:none">Event2<BR><BR>'
                      //  marqueecontent=marqueecontent +'<A href="http://www.annauniv.edu/tnea2006_new.doc" Style="text-decoration:none">Event3<BR><BR>'
                      //  marqueecontent=marqueecontent +'<A href="http://www.annauniv.edu/tnea2006_new.doc" Style="text-decoration:none">Event4<BR><BR>'
                        marqueecontent=marqueecontent +'</A><BR><BR>'
                        marqueecontent=marqueecontent + '</b></font>'  

                        marqueespeed=(document.all)? marqueespeed : Math.max(1, marqueespeed-1) //slow speed down by 1 for NS
                        var copyspeed=marqueespeed
                        var pausespeed=(pauseit==0)? copyspeed: 0
                        var iedom=document.all||document.getElementById
                        var actualheight=''
                        var cross_marquee, ns_marquee

function populate(){
    if (iedom){
            cross_marquee=document.getElementById? document.getElementById("iemarquee") : document.all.iemarquee
            cross_marquee.style.top=parseInt(marqueeheight)+8+"px"
            cross_marquee.innerHTML=marqueecontent
            actualheight=cross_marquee.offsetHeight
              }
    else if (document.layers){
            ns_marquee=document.ns_marquee.document.ns_marquee2
            ns_marquee.top=parseInt(marqueeheight)+8
            ns_marquee.document.write(marqueecontent)
            ns_marquee.document.close()
            actualheight=ns_marquee.document.height
              }
    lefttime=setInterval("scrollmarquee()",20)
}

window.onload=populate

function scrollmarquee(){
    if (iedom){
        if (parseInt(cross_marquee.style.top)>(actualheight*(-1)+8))
            cross_marquee.style.top=parseInt(cross_marquee.style.top)-copyspeed+"px"
        else
            cross_marquee.style.top=parseInt(marqueeheight)+8+"px"
               }
    else if (document.layers){
        if (ns_marquee.top>(actualheight*(-1)+8))
            ns_marquee.top-=copyspeed
        else
            ns_marquee.top=parseInt(marqueeheight)+8
                }
}

if (iedom||document.layers){
with (document){
if (iedom){
            write('<div style="position:relative;width:'+marqueewidth+';height:'+marqueeheight+';overflow:hidden" onMouseover="copyspeed=pausespeed" onMouseout="copyspeed=marqueespeed">')
            write('<div id="iemarquee" style="position:absolute;left:0px;top:0px;width:100%;">')
            write('</div></div>')
           }
else if (document.layers){
            write('<ilayer width='+marqueewidth+' height='+marqueeheight+' name="ns_marquee">')
            write('<layer name="ns_marquee2" width='+marqueewidth+' height='+marqueeheight+' left=0 top=0 onMouseover="copyspeed=pausespeed" onMouseout="copyspeed=marqueespeed"></layer>')
            write('</ilayer>')
            }
                }
}
</SCRIPT>
</DIV>
                  
                    
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
              <br><br>
              <hr style="border-width:2.0px; border-style:groove; border-color:rgb(210,225,228);">
              <center>
                  <span style="font-size: 14pt; font-family: Arial">
                  Welcome To TWAD Board<br>
                    Intranet Services
                  </span>        
              </center>
              <hr style="border-width:2.0px; border-style:groove; border-color:rgb(210,225,228);">
              <p align="justify">
                <span >
                &nbsp;&nbsp;&nbsp;The Intranet system for TWAD Board facilitates centralized data 
                storage and retrieval besides information analysis pertaining to 
                Project Monitoring, Financial Accounting and HR Management. 
                This system will be made available to all the offices of TWAD 
                Board through a network connecting the Head Office with all the 
                Regions, Circles and Divisions. 
                </span>
              </p>
              <hr style="border-width:2.0px; border-style:groove; border-color:rgb(210,225,228);">
            </div>
            </td>            
          </tr>           
        </table>
    </td>
    <td>
       <div id="Right" class="BodyRight">
  <!--     
       <table cellspacing="0" width="100%">
          <tr>
            <td>
              <div id=news class="NewsWnd">
                  <div id="headNews" class="LoginHead">&nbsp;&nbsp;News </div>             
                  <div id="bodyNews" >                   
                  
                   News goes here
                 
                      News will be listed here
                       
                  </div>
              </div>
                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
          </tr>          
          </table>
  -->     
       
       
      
          <table cellspacing="0" width="100%">
          <tr>
            <td>
              <div id=news class="NewsWnd" >
                  <div id="headNews" class="LoginHead" >&nbsp;&nbsp;News </div>
                 
                  <table><tr><td >
                  
                  <div id="bodyNews" align="justify" class="NewsWnd" >                   
                 
                 
                   <%
                                                        try
                                                        {
                                                          System.out.println("inside news");
                                                          results1=statement.executeQuery("select * from COM_MST_NEWS"); 
                                                         
                                                        while(results1.next())
                                                          {
                                                               System.out.println("aftr exe");
                                                              
                                                              //out.print("<a href=" + results.getInt("District_Code") + ">" + results.getString("District_Name") + "<BR>");                      
                                                               int eve_id=results1.getInt("NEWS_ID");
                                                               out.print("<a href=# onclick=openNwsWnd("+eve_id+") >"+results1.getString("NEWS_DESC") + "</a><BR><BR>");                      
                                                          }
                                                          //results.close();
                                                        }
                                                        catch(Exception e)
                                                        {
                                                            System.out.println("exception occured : " + e);
                                                        }      
                       %>
                      
                      
                  </div>
                  <div id="seemore" name="seemore" >
                <a id="more" name="more" href=# onclick="moreNews(this.id)" class="HeaderSeperator1">SeeMore...</a>
                 </div>
                  <div id="bodyNews1" style="visibility:hidden;" align="justify" >
                    
                      <a id="TopNews" name="TopNews" href=# onclick="moreNews(this.id)" class="HeaderSeperator1">TopNews only</a>
                      <br>
           <!--           <%
                                                        try
                                                        {
                                                          //results=statement.executeQuery("select * from news_list where news_date >(select now() )"); 
                                                          while(results1.next())
                                                          {
                                                              
                                                              //out.print("<a href=" + results.getInt("District_Code") + ">" + results.getString("District_Name") + "<BR>");                      
                                                               int eve_id=results1.getInt("NEWS_ID");
                                                               out.print("<a href=# onclick=openNwsWnd("+eve_id+") >"+results1.getString("NEWS_DESC") + "</a><BR>");                      
                                                          }
                                                          results1.close();
                                                        }
                                                        catch(Exception e)
                                                        {
                                                            System.out.println("exception occured : " + e);
                                                        }        
                       %>-->
                  </div>
                 </td></tr></table>
                
                 </div>
         
             </td>
          </tr> 
          
              
              
           
          </table>
       </div>
    </td>
  </tr> 
  <tr>
    <td colspan=3>
    <div id="bottom" class="HeaderSeperator">   
    <center>   
          Designed and Developed By<br>
              <a href=# onclick=open_New_Win('www.tn.nic.in') >National Informatics Centre</a>, Chennai.
    </center>    
    </div>
    </td>
  </tr>
  </table>  
  </body>
</html>
