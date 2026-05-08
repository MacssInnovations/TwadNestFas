<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Income and Expenditure</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" type="text/javascript" src="../scripts/MIS_Grouping_IncomeExpenditure_JS.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
  <script type="text/javascript" language="javascript">
    function loadyear_month()
    {
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
         document.miscRep.txtCB_Year.value=year
         document.miscRep.txtCB_Month.value=month;
    }
   
    </script>
  </head>
  <body class="table" onload="loadyear_month();">
  <form action="../../../../../../MIS_Grouping_IncomeExpenditure_Serv" name="miscRep" method="post" onsubmit="return checknull()">
  <%
        Connection connection=null;
        Statement statement=null;       
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
                connection.clearWarnings();
             }
             catch(SQLException e)
             {
                  System.out.println("Exception in creating statement:"+e);
             }          
           }
          catch(Exception e)
          {
             System.out.println("Exception in openeing connection:"+e);
          }
  %>
  <table cellspacing="2" cellpadding="3" border="1" width="100%">
  <tr>
        <td class="tdH" colspan="2"><center>Income and Expenditure</center></td>
  </tr>
   <tr align="left">
          <td class="table">
            <div align="left">
              Cash Book Year &amp; Month
            </div>
            </td>
            <td>
             <div align="left">
                <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
                <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
                      <option value="1">January</option>
                      <option value="2">February</option>
                      <option value="3">March</option>
                      <option value="4">April</option>
                      <option value="5">May</option>
                      <option value="6">June</option>
                      <option value="7">July</option>
                      <option value="8">August</option>
                      <option value="9">September</option>
                      <option value="10">October</option>
                      <option value="11">November</option>
                      <option value="12">December</option>
                  </select>
               </div>
          </td>
  </tr>
  
  <tr class="tdH">
      <td colspan="2">
          <div align="center">
            <input type=submit value=Submit >
            <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="javascript:self.close()">
          </div>
      </td>
   </tr>
  </table>
  </form>
  </body>
</html>