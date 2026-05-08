<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Preferences</title>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
    </style>
     <link href="../../../../../css/Sample3.css" rel="stylesheet"      media="screen"/>
  </head>
  <%
 
   HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    String strEmpId=Integer.toString(empid);
    System.out.println("****"+strEmpId);
  
 %>
  <body>
  <table align="center" bgcolor="#dcdcdc" border="0" cellpadding="1" cellspacing="0" width="95%">
  <tbody>
  <tr>
    <td align="center" class="tdH"><font color=#002173><b>Preferences</b></font><br>
      <table border="0" cellpadding="5" cellspacing="0" width="100%">
        <tbody>
        <tr>
          <td align="center" bgcolor="#ffffff">
            <table bgcolor="#ffffff" border="0" cellpadding="0" cellspacing="5" width="100%">
              <tbody>
              <tr>
                <td align="left" valign="top" class="table">
                  <table bgcolor="#ffffff" border="0" cellpadding="3" cellspacing="0" width="100%">
                    <tbody>
                    <tr>
                      <td align="left">
                        <table bgcolor="#ffffff" border="0" cellpadding="0" cellspacing="5" width="100%">
                          <tbody>
                          <tr>
                            <td align="left" valign="top">
                              <table border="0" cellpadding="2" cellspacing="0" width="100%">
                                <tbody>
                                <tr>
                                
                                <td align="left" class="tdH" valign="top" width="49%">
                                <%String profile=(String)session.getAttribute("profile");
                                if(profile!=null && profile.equalsIgnoreCase("twad"))
                                {
                                %>
                                <a href="View_EmpBasic_Details.jsp?txtEmpId=<%=strEmpId%>">Personal Information</a>
                                <%}else{%>
                                Personal Information
                                <%}%>
                                </td>
                                <td align="left" bgcolor="#ffffff" valign="top" width="2%">&nbsp;</td>
                                <td align="left"  valign="top" width="49%" class="tdH"><a href="#">Display 
                                Preferences</a></td></tr>
                                <tr>
                                <td align="left" class="table" valign="top" width="49%">This contains personal 
                                information about yourself such as your name, 
                                your mail address, etc.</td>
                                <td align="left" bgcolor="#ffffff" valign="top" width="2%">&nbsp;</td>
                                <td align="left" class="table" valign="top" width="49%">You can change the way that 
                                your A/C looks and displays information to 
                                you, such as the colors, the language, and other 
                                settings.</td></tr></tbody></table></td></tr></tbody></table>
                        <table bgcolor="#ffffff" border="0" cellpadding="0" cellspacing="5" width="100%">
                          <tbody>
                          <tr>
                            <td align="left" valign="top">
                              <table border="0" cellpadding="2" cellspacing="0" width="100%">
                                <tbody>
                                <tr>
                                <td align="center" class="tdH" valign="top" width="49%"><a href="ChangePasswordJSP.jsp">Change 
                                Password</a></td>
                                </tr>
                                <tr>
                                <td align="left" class="table" valign="top" width="49%">This connects to your local 
                                Password Server to change your A/C
                                password.</td>
                                </tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table>
  
  </body>
</html>