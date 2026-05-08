<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page  contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <script type="text/javascript"       src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"     src="../scripts/ForgetPasswordJS.js">     </script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    
 <link href="../../../../../css/CalendarControl.css" rel="stylesheet"      media="screen"/>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Forgot Password</title>
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
  <form name="frmforgetpassword" >
   <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="80%">
         <tr class="tdH" >
        <th align="center" colspan="2">
                Forgot Password 
                </th>
           </tr>
        
           
          <tr class="table">
            <td>
              <div align="left">
              Enter User Name
            </div></td>
            <td>
              <div align="left">
               <input type="text" name="txtusername" maxlength="15" size="15">
               </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
              Enter DOB (dd/mm/yy)
            </div></td>
            <td>
              <div align="left">
               <input type="text" name="txtdob" maxlength="10" size="10"
                             onfocus="javascript:vDateType='3';return checkempid();"
                             onblur="return checkdtdob(this);"
                             onkeypress="return calins(event,this);"/>
                             
                             <img src="../../../../../images/calendr3.gif"
                           onclick=" if(checkempid()==true)showCalendarControl(document.frmforgetpassword.txtdob);"
                           alt="Show Calendar"></img>
               </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
              Enter GPF No.
            </div></td>
            <td>
              <div align="left">
               <input type="text" name="txtgpf" maxlength="15" size="15" onfocus="return checkempid();"  onkeypress="return numbersonly(event,this);">
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