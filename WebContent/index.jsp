<%@ page contentType="text/html;charset=iso-8859-1" session="false"%>
<%@ page import="java.util.*" %>

<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"></meta>
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>TWAD Nest - Phase II welcomes you...</title>
     <style type="text/css"></style>
     <link href="images1/style.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript"  src="index.js">     </script>
   	 <script type="text/javascript"  src="org/Security/scripts/twad.js">     </script>
   	  <script type="text/javascript"  src="org/hrms/payroll/script/com_payroll_script.js"></script>

<link href="css/newstyle.css" rel="stylesheet" type="text/css" />
</head>
 <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
<body background="images/pagebg.jpg" style="background-repeat:repeat-x">
<form name="frmindex" id="frmindex" >
<table width="979" border="0" align="center" cellpadding="0" cellspacing="0">

  <tr>
    <td align="center" valign="top" background="images/topbg.jpg" style="background-repeat:no-repeat;"><table width="80%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="40" colspan="3" align="right" valign="middle" class="toptitle" style="padding-right:10px;">Welcome to TWAD Board Integrated E-Governance System</td>
      </tr>
	<tr><td colspan="3" align="right"><font size="2">Goto </font> <a href="https://track.tn.nic.in/twadonline" style='text-decoration:none'><font size="3"><b>Home</b></font></a></td></tr>
      <tr>
        <td height="130" colspan="3" align="left" valign="middle"><img src="images/logo.png" width="495" height="101" /></td>
      </tr>
      <tr>
        <td colspan="3"><div align="justify">The Intranet system for TWAD Board facilitates centralized data storage and retrieval besides information analysis pertaining to Project Monitoring, Financial Accounting and HR Management. This system will be made available to all the offices of TWAD Board through a network connecting the Head Office with all the Regions, Circles and Divisions.</div></td>
      </tr>
      <tr>
        <td height="120" colspan="3" align="right" valign="middle"><img src="images/twadnest.png" width="248" height="97" /></td>
      </tr>
      <tr>
        <td colspan="2" align="left" valign="middle"><img src="images/onlineServices.png" width="176" height="35" />
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <img src="images/fas.png" width="90" height="25"/>
        </td><td align="left" valign="middle" ></td>
      </tr>
      <tr> 
        <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="3" width="32%" align="center" valign="top">
	<table width="300" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="282" align="left" valign="bottom"><img src="images/index_box_topbg.png" width="300" height="13" /></td>
          </tr>
          
          <tr>
            <td colspan="2" bgcolor="#FFEE9D" class="serviceTitle" style="padding-left:15px;" align="center">Login</td>
          </tr>
          <tr><td bgcolor="#FFEE9D">&nbsp;</td></tr>
        
<tr>
<td>


	<table width="300" border="0" cellspacing="0" cellpadding="0">
	  
            <tr>
                <td bgcolor="#FFEE9D" width="40%" height="30%" align="right" valign="middle">
                  <b> <font color="black" size="2">User Name&nbsp;&nbsp;&nbsp;&nbsp;</font></b>
                  </td>
	  			<td bgcolor="#FFEE9D" width="60%" height="30%" align="center" valign="middle">
		  		<input type="text" name="txtID" tabindex="1" class="textbox" title="Enter UserName" maxlength="12" size="12" onblur="killChars(this);stripQuotes(this);"  >
               
				</td>
            </tr>
           <tr>
                <td bgcolor="#FFEE9D" width="40%" height="30%" align="right" valign="middle" >
                          <b> <font color="black" size="2">Password&nbsp;&nbsp;&nbsp;&nbsp;</font></b>
		        </td>
                <td bgcolor="#FFEE9D" width="60%" height="30%" align="center" valign="middle">        
                <input type="password" tabindex="2" name="txtPassword" class="textbox" maxlength="12" size="12"   title="Enter Password" onblur="killChars(this);stripQuotes(this);" >
		  		
		</td>
	
           </tr>
             
	      
                    <!--  <tr bgcolor="#FFEE9D" >
             <td width="40%" height="30%" align="right" valign="middle">
             <b> <font color="black" size="2">Enter Code &nbsp;&nbsp;&nbsp;</font></b>
             </td>
              <td width="60%" height="30%" align="center" valign="middle">
	<input type="text"  name="captchaval"  onKeyPress="return buttonsubmit(event)" id="captchaval" class="textbox"  size="12"  maxlength="7" title="Enter Code"  ></td>
	 
	
	</tr>-->
	  
                      <tr bgcolor="#FFEE9D" >
             <td width="100%" height="30%" align="center" valign="middle" colspan="2">
             <img src="captcha.jsp" style="height:35px;width:170px; " /><a href="index.jsp" onclick="Reload1()"><img src="images/reloadImage.png" tabindex="6" style="height:25px;width:25px;border:1; color:red;" title="Change Code"/></a>
            </td>
             
	
	 
	
	</tr>
     <!--  <tr>
                <td colspan="2" bgcolor="#FFEE9D"  align="center" valign="middle" height="35%">
                  <a id="loginid" href="#" onKeyPress="return buttonsubmit(event)">                            
                <img src="images/button.gif" alt="Login" name="butSubmit" width="60" height="18" vspace="5" border="1" onClick="return notNull()" /></a>    
		</td>
           </tr>-->
           
          
	 </table>
	 	<table width="300" border="0" cellspacing="0" cellpadding="0">
	  
          
             
	      
                   <tr bgcolor="#FFEE9D" >
             <td width="40%" height="30%" align="right" valign="middle">
             <b> <font color="black" size="2">Enter Above Code &nbsp;&nbsp;&nbsp;</font></b>
             </td>
              <td width="60%" height="30%" align="center" valign="middle">
	<input type="text"  name="captchaval" tabindex="3"  onKeyPress="return buttonsubmit(event,this)" id="captchaval" class="textbox"  size="12"  maxlength="7" title="Enter Code"  ></td>
	 
	
	</tr> 
	  
                    <!-- <tr bgcolor="#FFEE9D" >
             <td width="100%" height="30%" align="center" valign="middle" colspan="2">
             <img src="captcha.jsp" style="height:35px;width:170px; " /><a href="" onclick="Reload()"><img src="images/reloadImage.png" style="height:30px;width:30px;border:1; color:red;" title="Refresh Image"/></a>
            </td> -->
             
	
	 
	
	</tr>
     <tr>
                <td colspan="2" bgcolor="#FFEE9D"  align="center" valign="middle" height="35%">
                  <a id="loginid" href="#" onKeyPress="return buttonsubmit(event)">                            
                <img src="images/button.gif" tabindex="4" alt="Login" name="butSubmit" width="60" height="18" vspace="5" border="1" onClick="return notNull()" /></a>    
		</td>
           </tr>
           
          
	 </table>
	 
</td>
</tr>

          <tr>
            <td align="left" valign="top"><img src="images/index_box_bottombg.png" width="300" height="13" /></td>
          </tr>
       
</table>
	</td>
       
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      
    </table></td>
  </tr>
  <tr>
    <td height="60" align="center" valign="middle" background="images/footerbg.jpg" class="footer" style="background-repeat:no-repeat;">Website designed and hosted by <br />
      <a href="http://www.tn.nic.in" target="_blank" class="footer">National Informatics Centre, Chennai</a></td>
  </tr>
</table>
 </form>
</body>
<script type="text/javascript">
if( self != top ) { top.location.replace(self.location.href); }
</script>
</html>

<%
try
{
   String message=request.getParameter("message"); 
//   System.out.println("Message:"+message);
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
            else if(message.equals("captcha"))
           {            
                out.println("<script language='javascript'>alert(\"Please Enter the displayed code Correctly.\");</script>");
           }
           
    }
}catch(Exception e)
{
                // while loading first
}
%>