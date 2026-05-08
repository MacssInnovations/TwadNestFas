<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> Designation Wise Employee Summary Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/Status_Summary_ReportJS.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
      <!--div.scroll {	
      height: 100px;	
      width: 100%;	
      overflow: auto;	
      border: 1px solid #666;	
      background-color: #fff;	
      padding: 0px;
     visibility: hidden;
     position: relative;
      }-->
      
    </style>
</head>
<body onload="viewEmpbyDesg1('<%=request.getQueryString() %>')">
<br>
<table border='2' width="100%">
          <tr>
            <td colspan="2" align="left" valign="top" class="table" width="60%">
              <table border="0" width="100%">
             
                <tr>
                  <td colspan='2' class="tdH" align="center"><strong> <%=request.getParameter("designation") %> Employees List</strong></td>
                </tr><tr><td>
<div id="getemplist"></div></td></tr></table>
<center><input type="button" onclick="self.close();" value="close"></input></center>
</body>
</html>