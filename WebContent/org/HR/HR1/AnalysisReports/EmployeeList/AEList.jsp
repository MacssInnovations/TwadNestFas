<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>AEList</title>
    <script type="text/javascript">
   // function goNewWin() {

// Set height and width
var NewWinHeight=200;
var NewWinWidth=200;

// Place the window
var NewWinPutX=10;
var NewWinPutY=10;

//Get what is below onto one line
window.document.title = "heyhey";
//window.document.statusbar.enable = false;
alert('this part works')
TheNewWin =window.open("http://10.163.0.58:8080/IntelliVIEWSDK/viewDynamicReport.jsp?PageName=AH258c3f5db57237265e72ed877cb328e4","TheNewpop",
'titlebar=no,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no'); 
//TheNewWin.document.title = "heyhey";
//Get what is above onto one line

TheNewWin.resizeTo(NewWinHeight,NewWinWidth);
TheNewWin.moveTo(NewWinPutX,NewWinPutY);

//}
    
   // window.open("http://10.163.0.58:8080/IntelliVIEWSDK/viewDynamicReport.jsp?PageName=AH258c3f5db57237265e72ed877cb328e4");
    </script>
  </head>
  <body>
  <form >
  </form>
  </body>
</html>