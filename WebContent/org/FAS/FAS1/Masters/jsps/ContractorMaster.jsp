<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252" %>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
<meta http-equiv="cache-control" content="no-cache">
<title>            Contractor Master Details   </title>
<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="../projectMaster.js"></script>
<link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
</head>
 <body bgcolor="rgb(255,255,225)">
 <form name="frmProjectMaster" id="frmProjectMaster" >
   <%
  
      Connection con=null;
      ResultSet rs=null,rs2=null;
      PreparedStatement ps=null,ps2=null;
      ResultSet results=null;
      ResultSet results1=null;
      ResultSet results2=null;
       try
      {
      
                 ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                String ConnectionString="";
    
                String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                String strdsn=rs1.getString("Config.DSN");
                String strhostname=rs1.getString("Config.HOST_NAME");
                String strportno=rs1.getString("Config.PORT_NUMBER");
                String strsid=rs1.getString("Config.SID");
                String strdbusername=rs1.getString("Config.USER_NAME");
                String strdbpassword=rs1.getString("Config.PASSWORD");
    
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
          
        System.out.println("user id::"+empProfile.getEmployeeId());
        int empid=empProfile.getEmployeeId();
        //int empid=9315;
        int  oid=0;
        String oname="";
   
    try
    {
           
            ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                 }
            results.close();
            ps.close();
            ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                  }
            results.close();
            ps.close();
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>

 <table cellspacing="2" cellpadding="3" border="1" width="100%">
 <tr class="tdH" align="center">
    <td colspan="2">
            Contractor Details Master
    </td>
 </tr>
 <tr class="table">
    <td >
        <div align="left"> Office Code & Name: </div>
    </td>
    <td>
        <div align="left">   
        <input type="text" name="txtOfficeId"  id="txtOfficeId" 
        value="<%=oid%>" size="5" readonly="readonly" class="disab"  />
        <input type="text" name="txtOfficeName"  id="txtOfficeName" 
        value="<%=oname%>" size="30" readonly="readonly" class="disab" />
        </div>
    </td>
 </tr>
 <tr class="table" align="left">
 <td>
    <div>
                Contractor Id
    </div>
 </td>
 <td>
    <div>
            <input type="text" size="7" name="txtProjectId" id="txtProjectId"  readonly="readonly" class="disab" /> (System Generated)
    </div>
 </td>
 </tr>
 <tr class="table" align="left">
 <td>
    <div>
                Contractor Name
    </div>
 </td>
 <td>
    <div>
            <input type="text" size="50" maxlength="200" name="txtProjectName" id="txtProjectName" /> 
    </div>
 </td>
 </tr>
  <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction('Add','null')" tabindex="4"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="UPDATE" id="cmdUpdate" style="display:none" onclick="doFunction('Update','null')" tabindex="5"/>
                     </td>
                    <!-- <td>
                    <input type="button" name="cmdDelete" value="DELETE" id="cmdDelete" style="display:none" onclick="doFunction('Delete','null')" />
                     </td>-->
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()"/>
                     </td>
                       <td>
                     <input type="button" id="Exit" name="Exit" value="EXIT" onclick="self.close()">
                     </td>
                </tr>
             </table>
                </div></td>
            </tr>       
           
 </table>
 <br>
 <table cellpadding="3" cellspacing="2" width="100%" border="1">
 <tr align="center" class="tdH">
 <td>
        Contractor Id
 </td>
 <td>
        Contractor Name
 </td>
 
 </tr>
 <tbody align="center" id="tab_body" class="table">
 <%
        String sql2="select CONTRACTOR_ID,CONTRACTOR_NAME from PMS_MST_CONTRACTORS_VIEW where CONTRACTOR_ID="+oid;
        Statement d=con.createStatement();
        rs=d.executeQuery(sql2);
        PreparedStatement ps3=con.prepareStatement(sql2);
        //ps3.setInt(1,oid);
        //rs3=ps3.executeQuery();
        
 %>
 </tbody>
 </table>
 </form>
  
    </body>
</html>