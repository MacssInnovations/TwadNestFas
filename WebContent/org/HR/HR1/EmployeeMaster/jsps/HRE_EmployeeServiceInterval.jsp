<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Employee Verify form</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
  </head>
  <% 
        HttpSession session=request.getSession(false);
        String str=(String)session.getAttribute("interval");
       session.removeAttribute("interval");
        
    %>
  <body>
  <form name="frmInterval">
 
      <table cellspacing="2" cellpadding="3" border="0" width="80%"
             align="center" class="table">
        
       <% 
       
       if(str!=null)
        {
            out.println(str);
            //System.out.println("str:"+str);
        }
        else
        {
                out.println("Please Validate Again..");
        }
     
        
    %>
        
        <tr class="tdH">
          <td colspan="3">
            <div align="center">
              <input type="BUTTON" value="  EXIT  " onclick="self.close();"/>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>