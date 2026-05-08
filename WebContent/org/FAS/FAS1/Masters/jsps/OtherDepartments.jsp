<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page  session ="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>

<script type="text/javascript" src="../scripts/OtherDepartments.js">    </script>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <link href="../../../../../css/Sample3.css" rel="stylesheet"           media="screen"/>
 
    <title>OtherDepartments</title>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a{ color: #0000ff;
        text-decoration:none;
      }
     
    </style>
  </head>
  <%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  
  
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

           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
  <body>
  
  <form name="OtherDepartmentsReport"
              action="../../../../../OtherDepartmentsDetails.con" 
              method="POST">
      <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
            <td colspan="2" align="center"><b>Other Departments</b></td>
        </tr>
         <tr class="table">
                  <td align="left">Other Departments</td>
                  <td  align="left">
                    <select size="1" name="cmbControllingLevel"
                            id="cmbControllingLevel"
                            onchange="doFunction('loadOffices')">
                      <option value="">----Select Department----</option>
                      <%
                                                      try
                                                      {
                                                        ps=connection.prepareStatement("select other_dept_Id,Other_dept_Name from HRM_MST_OTHER_DEPTS order by Other_dept_Name");
                                                        results=ps.executeQuery(); 
                                                        while(results.next()) 
                                                        {
                                                            out.print("<option value='" + results.getString("other_dept_Id") + "'>" + results.getString("Other_dept_Name") + "</option>");                      
                                                        }
                                                        results.close();
                                                      }
                                                      catch(Exception e)
                                                      {                        
                                                      }      
                                                %>
                    </select>
                  </td>
                 </tr>
                 <!-- <tr class="table">
  <td align="left">Office Name&nbsp;&nbsp;</td>
  <td align="left">
   <select size="1" name="office_id" id="txtOffice_id" onchange="doFunction('loadDetails')">
                      <option value="0">--Select any Office--</option>
                    </select>
  </td>      -->               
  </tr>

 
                <tr class="table">
           <td align="left">Report&nbsp;Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbReportType">
                  <option value="PDF" selected="selected">PDF</option>
                  <option value="EXCEL">EXCEL</option>
                <!--  <option value="TXT">TXT</option>-->
                  <option value="HTML">HTML</option>
                </select> </td>
           <td align="left">&nbsp;</td>
          </tr>
             
        </table>
          <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
      <tr class="tdH">
      <td >&nbsp;</td>
      </tr>
      
      </table>
      </div>
      <input type="hidden" name="txtseloff">
      <center><input type="submit" value="submit"></center>
    </form>
    
  </body>
</html>