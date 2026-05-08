<%@ page contentType="text/html;charset=windows-1252" session="false"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"></meta>
    
    <title>Twad Board Intranet Services</title>
    <link href="css/index.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript"     src="index.js">     </script>
    <script type="text/javascript"     src="org/Security/scripts/twad.js">     </script>
  
    <script language="javascript" type="text/javascript">
 
    </script>
  </head>
  <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
  
  <table cellspacing="0" cellpadding="0"  border="2"    width="100%" height="100%">
    
      <tr >
        <td colspan="3" valign="top" height="10">
            <table cellpadding='0' cellspacing='0' border='0' >
                <tr>
                 <td  align='left'  rowspan="3"><img alt="TWAD Board Online Services" src="images\twad_logo2.gif"></img></td>
                    <td  align='left'><img alt="TWAD Board Online Services" src="images\twadhead.gif"></img></td>
                </tr>
                <tr>
                    <td  align='left' ><img alt="TWAD Board Online Services" src="images\twadanimat.gif"></img></td>
                    
                </tr>
                <tr><td >&nbsp;</td></tr>
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
                        <!--<form name="frmindex" method="POST" onsubmit="return notNull()">-->
                          <form name="frmindex" id="frmindex"  >
                          <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td colspan="2">&nbsp;</td>
                            </tr>
                            <tr>
                              <td>User&nbsp;Name</td>
                              <td>
                                <input type="text" name="txtID" maxlength="12"
                                       size="12"  ></input>
                              </td>
                            </tr>
                            <tr>
                              <td>Password</td>
                              <td>
                                <input type="password" name="txtPassword"
                                       maxlength="12" size="12" onkeypress="return buttonsubmit(event)"  ></input>
                              </td>
                            </tr>
                            <tr>
                                <td colspan="2">&nbsp;</td>
                            </tr>
                            <tr>
                              <td colspan="2" align="center">
           
                              <a id="loginid" href="#" onkeypress="return buttonsubmit(event)" ><img  src="images/Login_but.JPG" onclick="return notNull()"
                                       name="butSubmit" alt="Login" 
                            style="border-width:2.0px; border-color:rgb(208,229,233); border-style:groove;"></a>
                        
                              <!--  <input type="image" 
                                       src="images/Login_but.JPG"
                                       name="butSubmit" alt="Login"
                                       style="border-width:2.0px; border-color:rgb(208,229,233); border-style:groove;"/>-->
                              </td>
                            </tr>
                            <tr>
                              <td colspan="2" class="forgetPwd"
                                  
                                  align="center" valign="center">
                                <img src="images/index.png" alt="Fogot Password"></img>
                        <!--     <a style="text-decorate:none;cursor:hand" onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/org/HR/HR1/EmployeeMaster/jsps/ForgetPasswordJSP.jsp')">Forgot&nbsp;Your&nbsp;Password?</a> -->
                            <a style="text-decorate:none;cursor:hand">Forgot&nbsp;Your&nbsp;Password?</a> 
                               </td>
                            </tr>
                          </table>
                        </form>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr style="height:50%">
                <td>
                  <div id="events" class="EventWnd" style="height:100%">
                    <div id="eventHead"  class="LoginHead">&nbsp;&nbsp;Events</div>
                     
                    <div id="eventbody" >
                      <!-- 
                  
                  event body
                  
                  -->
                       Events will be listed here
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
                  <div align="center"/>
                  
                  <div align="center">
                    <!--<h4>-->
                      <!--<strong>-->
                      <span style="font-size: 14pt; font-family: Arial">
                          <font color="#000000">
                             
                          </font>
                        </span>
                        
                        <span style="font-size: 14pt; font-family: Arial">
                          Welcome To 
                          <br/>
                        </span>
                        <span style="font-size: 16pt; font-family: Monotype Corsiva">
                          <b>TWADNEST</b>
                          <br/>
                        </span>
                        <span style="font-size: 14pt; font-family: Arial">
                          <font color= '#FF552A'><b>TWAD</b></font> Board I<font color='#FF552A'><b>N</b></font>tegrated
                        </span>
                        
                        <span style="font-size: 14pt; font-family: Arial">                          
                       <font color="#FF552A"><b>E</b></font>-Governance <font color='FF552A'><b>S</b></font>ys<font color='FF552A'><b>T</b></font>em
                       </span>
                       <!--</strong>-->
                    <!--</h4>-->
                  </div>
                  
                  <hr style="border-width:2.0px; border-style:groove; border-color:rgb(210,225,228);"></hr><div align="left">
                     
                    <span>
                      &nbsp;The 
                      <strong>TWADNEST </strong>
                      system for TWAD Board facilitates centralized data storage
                      and retrieval besides information analysis pertaining to
                      Project Monitoring, Financial Accounting and HR
                      Management. This system will be made available to all the
                      offices of TWAD Board through a network connecting the
                      Head Office with all the Regions, Circles and Divisions.
                    </span>
                     
                  </div><hr style="border-width:2.0px; border-style:groove; border-color:rgb(210,225,228);"></hr>
                  <!--  Place greeting here (start)  -->


                  <!--  Place greeting here (end) --> 
                </div>
              </td>
            </tr>
          </table>
        </td>
        <td >
          <div id="Right" class="BodyRight" style="height:100%">
            <table cellspacing="0" width="100%" border="0" style="height:100%" >
              <tr >
                <td valign="top">
                  <div id="news" class="NewsWnd" style="height:100%">
                    <div id="headNews" class="LoginHead">&nbsp;&nbsp;News</div>
                     
                    <div id="bodyNews" style="height:100%">
                    
                      <center>
                      <a style="text-decorate:none;cursor:hand;color:blue"  onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/e_code_for_858_employees.pdf')">Employee Code for 858 Employees</a> </center>
                      
                      <p align="center">
                        <a target="_blank" style="text-decorate:none;cursor:hand;color:blue"  href="newly_appointed_AE_emp_codes_allocated.pdf">
                          Emp.Code for New AEs
                        </a>
                      </p>
                      
                      <p align="center">
                        <a target="_blank" style="text-decorate:none;cursor:hand;color:blue"  href="emp_app_nov.pdf">
                          Emp.Code for Employees Appointed during Nov,2007
                        </a>
                      </p>
                      
                    </div>
                  </div>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
              </tr>
              
              
              <tr >
                <td valign="top">
                  <div id="notice" class="NewsWnd" style="height:100%">
                    <div id="headNotice" class="LoginHead">&nbsp;&nbsp;Notice Board</div>
                     
                    <div id="bodyNotice" style="height:100%">
                    <center>  <a style="text-decorate:none;cursor:hand;color:blue" onclick="javacript:loadPageInNewWindow('<%=request.getContextPath()%>/viewbirthday.jsp')">Birthday Wishes</a> </center>
                     
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
              Designed and Developed by <a href="http://www.tn.nic.in" target="_blank" style="text-decoration = underline ;color=black">National Informatics Centre, Chennai.</a>
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
           else if(message.equals("retired"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Your profile has to\nbe updated. Please contact \nSystem Administrator.</label>");    
        out.println("<script language='javascript'>alert('Login is disabled based on Employee Retirement data.');</script>");
           }
           else if(message.equals("dbnill"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Your profile has to\nbe updated. Please contact \nSystem Administrator.</label>");    
        out.println("<script language='javascript'>alert(\"Database Service is not available.\\n Please Contact System Administrator.\");</script>");
           }
            else if(message.equals("logindisabled"))
           {            
                out.println("<script language='javascript'>alert(\"Login Disabled.\");</script>");
           }
    }
}catch(Exception e)
{
                // while loading first
}
%>