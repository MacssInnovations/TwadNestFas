<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/> 
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Asset List</title>
    <script type="text/javascript" src="../scripts/AssetListJS.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   
  </head>
  <body  bgcolor="rgb(255,255,225)" onload="loadfin_year();">
   <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    
     Connection connection=null;

  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
    
   try
  {
  
             ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
  
  <% 
      
      HttpSession session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
      
   %>
  
  <form name="AssetList" id="AssetList">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              LIST OF ASSETS
            </div></td>
        </tr>
       <tr class="table">
        
          <td>
            Financial year <font color="#ff2121">*</font>
           </td>
           <td>
            <input type="text" name="txtFinYearvalue" id="txtFinYearvalue" maxlength="9" 
                   size="10" />
           </td>
        </tr>
        <tr class="table">
        
          <td>
            By Classification Of Asset
           </td>
           <td>
            <select size="1" name="comClasAss" id="comClasAss"
                    onchange="doFunction('comClasAss','null')">
              <option value="">----Select----</option>
              <option value="All">All</option>
              <%
               PreparedStatement ps1=null;
               ResultSet rs1=null;
                try
                {
                ps1=con.prepareStatement("select ASSET_CLASS_CODE,ASSET_CLASS_DESC from COM_MST_ASSETS_CLASS order by ASSET_CLASS_CODE");
                
                rs1=ps1.executeQuery();
                while(rs1.next())
                {
                int des1=rs1.getInt("ASSET_CLASS_CODE");
                out.println("<option value="+des1+">"+rs1.getString("ASSET_CLASS_DESC")+"</option>");
                }
                rs1.close();
                ps1.close();
                }
                catch(Exception ae)
                {
                System.out.println("exception in asset types...."+ae);
                }
                
                
                rs1.close();
                ps1.close();
                %>
            </select>
            </td>
        </tr>
        <tr class="table" style="display:none"> <!--  will be used in case of vehicles -->
          <td>
          By OwnerShip ( for vehicle types)
          </td>
          <td>
            <select size="1" name="comOwnerShip" id="comOwnerShip"
                    onchange="doFunction('comOwnerShip','null')">
              <option value="">----Select----</option>
              <option value="All">All</option>
              <option value='B'>Owned by Board</option>
              <option value='H'>Hired</option>
              <option value='D'>Donated</option>
            </select>
            </td>
        </tr>
        
     <!--  <tr class="table">
       <td colspan="2">
        <input type="button" value="GO" onclick="retriveDATA()"> 
        </td>   
        </tr>-->
      </table>
       <table cellspacing="2" cellpadding="3" border="1" width="100%">
       <tr class="tdH">
       <th>
       Select
       </th>
       <th>
       Asset Code
       </th>
       <th>
       Asset Description
       </th>
       
       <th>
       Month / Year of Purchase
       </th>
     <!--  <th>
        Location Currently Used at
       </th>  -->
       <th>
       Original Cost
       </th>
       <th>
      Current Vlaue
       </th>
       <th>
       Status
       </th>
       
      
       </tr>
       <tbody id="tb" class="table" align="left">
         
          
          </tbody>
       </table>
        <table align="center" cellspacing="2" cellpadding="3" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" id="cmdcancel" name="cancel" value="Exit"
                     onclick=" self.close();"></input>
            </div>
          </td>
        </tr>
      </table> 
    </form></body>
</html>