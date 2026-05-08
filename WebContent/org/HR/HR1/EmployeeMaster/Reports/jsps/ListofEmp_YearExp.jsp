<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>List of Employees Years Completion(Seniority Based)</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/ListofEmp_YearExpJS.js"></script>
    
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
 
  
  
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
  
  <body>
  <form name="frmlist_service" action="../../../../../../ListofEmp_YearExp_Serv" method="post">
  
  <!--action="ListofEmp_YearExp_Serv" method="post"-->
  <table cellpadding="3" cellspacing="2" border="1" width="100%"> 
  
  <tr class="tdH">
  <th colspan="2" align="center">List of Employees Years Completion(Seniority Based)</th>
  </tr>
  
  <tr class="table">
   <td colspan="2" align="left">
     <input type="radio" name="optselect" value="Desig" id="optselect1" onclick="selectoption1()" checked="checked">Designation
     <input type="radio" name="optselect" value="Cadre" id="optselect2" onclick="selectoption1()">Cadre
   </td>
   </tr>
   
   <tr class="table" id="divdest">
   <td colspan="2">
   <div align="left">
   <select name="cmbsgroup" id="cmbsgroup" onclick="getDesignation()">
   <option value="0">Select Service Group</option>
   <%
     ResultSet rs=null;
     
     try
     {
     ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
     rs=ps.executeQuery();
     
     int strcode=0;
     String strname="";
     
     while(rs.next())
     {
       strcode=rs.getInt("SERVICE_GROUP_ID");
       strname=rs.getString("SERVICE_GROUP_NAME");
       
       out.println("<option value='"+strcode+"'>"+strname+"</option>");
     }
     }
     catch(Exception e)
     {
       System.out.println(e.getMessage());
     }
     finally
     {
       rs.close();
       ps.close();
     }
   %>
   </select>
   
   <select name="cmbDesignation" id="cmbDesignation" onclick="return checkgroup1();">
   <option value="0"></option>
   </select>
   </div>
   </td>
   </tr>
   
   
   <tr class="table" id="divcadre" style="display:none">
   <td colspan="2">
   <div align="left">
   <select name="cmbsgroup1" id="cmbsgroup1" onclick="getCadre()">
   <option value="0">Select Service Group</option>
   <%
      ResultSet rs1=null;
      
       try
       {
          ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
          rs1=ps.executeQuery();
          
          while(rs1.next())
          {
             int strcode=0;
             String strname="";
             
             strcode=rs1.getInt("SERVICE_GROUP_ID");
             strname=rs1.getString("SERVICE_GROUP_NAME");
             
             out.println("<option value='"+strcode+"'>"+strname+"</option>");
          }
       }
       catch(Exception e)
       {
         System.out.println(e.getMessage());
       }
       finally
       {
         rs1.close();
         ps.close();
       }
   
   %>
   </select>
   
   <select name="cmbCadre" id="cmbCadre" onclick="return checkgroup2();">
   <option value="0"></option>
   </select>
   
   </div>
   </td>
   </tr>
   
   <tr class="table">
   <td align="left">Report&nbsp;Type   
   </td>
   <td>
   <select name="cmbReportType">
   <option value="PDF" selected="selected">PDF</option>
   <option value="EXCEL">EXCEL</option>
   <option value="HTML">HTML</option>
   </select>
   </tr>
   
   <tr class="tdH">
   <td colspan="2">
     <div align="center">
       <input type="submit" name="submit" value="Submit" />
       <input type="reset" value="Clear"/>
       <input type="button" value="Exit" onclick="self.close()"/>       
     </div>
   </td>
   </tr>
  
  </table>
  </form>
  </body>
</html>