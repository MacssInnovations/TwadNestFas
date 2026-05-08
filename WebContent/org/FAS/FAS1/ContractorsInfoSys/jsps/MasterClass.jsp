<!--
    File Name     : MasterClass.jsp
    Purpose       : To create form that allows us add,modify and delete records residing in database
    References    : AjaxClasss.js,Sample.css
    Servlet Ref.  : ServletClass.java
-->

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*"%>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <title>Master Class</title>
          <script type="text/javascript">
            var edit=false; 
            function popupWindow()
                {
                      my_window= window.open("ListClass.jsp","mywindow1","status=1,height=500,width=600"); 
                      my_window.moveTo(250,250);    
                }
                            
          </script>
          <script type="text/javascript" src="../scripts/AjaxClass.js"></script> 
          <!--<link href="../../../../../css/green.css" rel="stylesheet" media="screen"/>-->
          <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
            <link href="../../../css/Sample.css" rel="stylesheet" media="screen"/>      
      
    </head>
 <body>
   <form name="frmClassRegistration">
     <table cellspacing="2" cellpadding="3" border="1" width="90%" align="center" class="table">
       <tr>
         <td colspan="2" class="td">
           <DIV align="center">
             <STRONG><FONT face="Times New Roman" size="5">Master Class Details</FONT></STRONG>
           </DIV>
         </td>
       </tr>
       <tr>
         <td>
           <FONT face="Times New Roman">Registration Class Id</FONT>
         </td>
         <td>
          <input type="text" name="txtRegClassId" size="4" maxlength="4" onkeyup="isInteger(this,event)" onblur="Verification()"/>
         </td>
       </tr>
       <tr>
         <td>
           <FONT face="Times New Roman">Registration Class Description</FONT>
           </td>
         <td>
          <input type="text" name="txtRegClassName" maxlength="25" size="10" />
         </td>
       </tr>
       <tr>
         <td colspan="2">
         <input type="Button" value="  Add  " onclick="addRecord(); " name="cmdAdd"> 
                                          <input type="Button" value="  Edit  " onclick="promptID()" name="cmdEdit"> 
                                          <input type="Button" value=" Update" onclick="callServer('Update','null')" disabled name="cmdUpdate"> 
                                          <input type="Button" value=" Delete" onclick="callServer('Delete','null')" disabled name="cmdDelete">
                                          <input type="Button" value="Clear All" onclick="clearAll()" name="cmdClearAll">
                                          <input type="Button" value="List All Registration Class" onclick="popupWindow()" name="cmdListAll" >
         
         </td>
       </tr>
     </table>
   </form>    
        
          
         
                

