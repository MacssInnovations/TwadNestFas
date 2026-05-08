<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%--<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>--%>
<html>
  <head>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"     src="../scripts/ResetPasswordJS_FAS.js">     </script>
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Reset Password (Based on Jurisdiction)</title>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
    </style>
      <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
  </head>
  <body>
  <form name="frmchangepassword" >
   <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="80%">
         <tr class="tdH" >
        <th align="center" colspan="2">
                Reset Password (Based on Jurisdiction)
                </th>
           </tr>
        
           
          <tr class="table">
            <td>
              <div align="left">
                User Id<font color="#ff2121">
                  <font color="#ff2121">
                    *
                  </font>
                </font>
              </div></td>
            <td>
              <div align="left">
               <input type="text" name="txtUserId" maxlength="10" size="10" onchange="doFunction('Login','null')"/>
               </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
              Enter New Password<font color="#ff2121">
                  <font color="#ff2121">
                    *
                  </font>
                </font>
              </div></td>
            <td>
              <div align="left">
               <input type="password" name="txtnewPassword" maxlength="15" size="15" onfocus="return checkoldpass()">
               </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
              Enter Confirm Password<font color="#ff2121">
                  <font color="#ff2121">
                    *
                  </font>
                </font>
              </div></td>
            <td>
              <div align="left">
               <input type="password" name="txtconfirmPassword" maxlength="15" size="15" onfocus="return checkoldpass()">
               </div>
            </td>
          </tr>
          
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
              <input type="button" value="Submit" name="submit" onclick="doFunction('test','null')">
                 </div>
            </td>
          </tr>
        </table>
      </div>
      </form>
      </body>
</html>