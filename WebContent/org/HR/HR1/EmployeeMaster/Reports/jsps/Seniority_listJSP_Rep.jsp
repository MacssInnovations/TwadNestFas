<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
  
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
      <script type="text/javascript"       src="../scripts/CalendarControl.js"></script>
      <script type="text/javascript" src="../scripts/Seniority_listJS_Rep.js"></script>
      
      
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <meta http-equiv="cache-control" content="no-cache">
      <title>Seniority List Details</title>       
          
          <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
          </script> 
             <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
          <!--<link href='../../../../../css/RWS_CSSColour.css' rel='stylesheet' media='screen'/>-->
          <link href='../../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
          
          <style type="text/css">
                .divClass{display: none;  }                                
          </style>
    </head>
 <body class="bodyid"> 
 <%
   Connection connection=null;
   Statement statement=null;
   ResultSet results=null;   

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

       
       try
       {
            statement=connection.createStatement();
            connection.clearWarnings();
       }
       catch(SQLException e)
       {
              System.out.println("Exception in creating statement:"+e);
              return;
       }          
  }
  catch(Exception e)
  {         
         System.out.println("Exception in openeing connection:"+e);
         return;
  }  
 %>  
 <%
          
          PreparedStatement ps=null;
          ResultSet rs1=null;
          PreparedStatement ps3=null;
          ResultSet rs3=null;
 
          HttpSession session=request.getSession(false);
          UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
          
          System.out.println("user id::"+empProfile.getEmployeeId());
          int empid=empProfile.getEmployeeId();
          int  oid=0;
          String oname="",oadd1="",oadd2="",ocity="",odist="",olid="",owid="";
          String olname=""; 
          String ownature="";
          
           
  %>
        <form name="frmSeniority" 
                       id="frmSeniority">                
         <div align="center">
         <table  cellspacing="3" cellpadding="1"  width="100%" border="1">                       
         <tr>
            <td class="tdH" colspan="2">
                <center>                           
                <h3>Seniority List Details</h3>                            
                </center>
            </td>
         </tr>                         
          <tr class="table">
            <td>
              <div align="left">Select Cadre
               <font color="#ff2121">*</font>
              </div>
            </td>
                        
            <td>
                      <div align="left">
                    <select name="cmbsgroup" id="cmbsgroup" onchange="getDesignation()" >
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
            System.out.println("Exception in grid.."+e);
          }
           finally
          {
                rs.close();
                ps.close();
          
          }    
                
        %>              
               </select> 
               
                      <select name="cmbDesignation" id="cmbDesignation" 
                              onclick="return checkGroup();" onchange="doFunction('getDet','null')">
                        <option value="0"></option>
                        
                      </select>
                    </div>                      
                    </td>
          </tr>
          
           <tr class="tdTitle">
                  <td colspan="2">
                  <div align="center">
                   <input type="button" name="btSub" id="btSub" value="Submit" onclick="doFunction();"/>
                   <input type="button" name="btexit" id="btexit" value="Exit" onclick="closeWindow();"/> 
                  </div>
                  </td>
           </tr>
            </table>
            </div>
        </form>
  </body>
</html>

