<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>CreateIssue</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../scripts/CreateHelp.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                
                isIE=document.all? 1:0
       
                function noEnter(e)
                 {
       
                  keyEntry = !isIE? e.which:event.keyCode;
                  
                  if(keyEntry=='38')
                  {
                    return false;
                  }
                 }
          </script> 
  </head>
  <body class="table">
  
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
              //System.out.println("Exception in creating statement:"+e);
       }          
  }
  catch(Exception e)
  {
         //System.out.println("Exception in openeing connection:"+e);
  }
  
  %>

  
  <form action="../../../CreateServletHelp.con" name="frmHelp" onsubmit="return nullcheck()" method="post">
    <table width="100%" >
        <tr>
            <td class="tdH"><center><b>Create Issue</b></center></td>
        </tr>
          <tr>
            <td>
                <table border=1 cellspacing="3" cellpadding="1" width="100%">
                    <tr>
                        <td>
                            UserName:
                        </td>
                        <%
                        System.out.println("hai");
                        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                        System.out.println("emp"+empProfile);
                        int Emp_Id=empProfile.getEmployeeId();
                        String empname="";
                        String Level="";
                        String officename="";
                        System.out.println("empid"+Emp_Id);
                        try
                        {
                        PreparedStatement ps=connection.prepareStatement("select employee_name||employee_name||case when employee_initial is null then ' ' else '.'||employee_initial end as employee_name from hrm_mst_employees where employee_id=?");
                        ps.setInt(1,Emp_Id);
                        ResultSet res=ps.executeQuery();
                        if(res.next())
                        {
                            empname=res.getString("employee_name");
                            System.out.println("empname:"+empname);
                        }
                         Level=empProfile.getOfficeLevel();
                         System.out.println("level"+Level);
                         
                        try{
                        ps=connection.prepareStatement("select a.office_name from com_mst_offices a,hrm_emp_current_posting b where a.office_id=b.office_id and  b.employee_id=?");
                        ps.setInt(1,Emp_Id);
                        res=ps.executeQuery();
                        if(res.next())
                        {
                            officename=res.getString("office_name");
                            System.out.println("office name:"+officename);
                        }
                        }
                        catch(Exception e){System.out.println("Error in Office Name:"+e);}
                     %>
                        <td>
                            <input type=text name=txtUserName size=30 value=<%if(empname!="null"){%>"<%=empname%>" <%}%> disabled >
                            <input type=hidden name=txtHUserName value=<%if(empname!="null") {%>"<%=empname%>" <%}%> >
                            
                        </td>
                    <%
                        }catch(Exception e)
                        {
                            System.out.println("Exception in Catch:"+e);
                        }
                    %>
                        <td>
                        Major System:<label style="color:rgb(255,0,0);">*</label>
                        </td>
                        <td>
                        
                        <select name="cmbMajor" id="cmbMajor" onchange="minor()">
                                    <option value=0>--Select Major System--</option>
                                    <%
                                        try{
                                        
                                        PreparedStatement ps1=connection.prepareStatement("select major_system_id,major_system_desc from sec_mst_major_systems where major_system_id not in ('ASA','HLD')");
                                        ResultSet res1=ps1.executeQuery();
                                        while(res1.next())
                                        {
                                            out.println("<option value="+res1.getString("major_system_id")+">"+res1.getString("major_system_desc")+"</option>");
                                            
                                        }
                                    }catch(Exception e)
                                    {
                                        System.out.println("Exception in major:"+e);
                                    }
                                    %>
                                    </select>
                        </td>
                        
                    </tr>
                    <tr>
                        <td>
                            OfficeLevel:
                        </td>
                        <td>
                            <input type=text name=txtUserType size=30 value=<%if(Level!="null"){%>"<%=Level%>" <%}%>disabled>
                        </td>
                        <td>
                            Minor System:<label style="color:rgb(255,0,0);">*</label>
                        </td>
                        <td>
                            <select name="cmbMinor" id="cmbMinor" onfocus="issue()">
                                         <option value=0>--Select Minor System--</option>
                                         </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Office Name</td>
                        <td><input type="text" name="txtofficename" readonly size="40" value="<%=officename%>">
                        </td>
                        
                        <td>
                            Priority:<label style="color:rgb(255,0,0);">*</label>
                        </td>
                        <td >
                            <select name="cmbPriority" id="cmbPriority" onfocus="issue()">
                            <option value="High">High</option>
                            <option value="Medium">Medium</option>
                            <option value="Low">Low</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdH" colspan="4"><b>Problem Description:</b>
                        </td>
                    </tr>
                    <tr>
                        <td >Subject:<label style="color:rgb(255,0,0);">*</label>
                        </td>
                        <td colspan="3">
                            <input type=text name=txtSubject size=60 onfocus="issue()"
                                                     onkeypress="return noEnter(event)">
                        </td>
                    </tr>
                    <tr>
                        <td >
                            Description:<label style="color:rgb(255,0,0);">*</label>
                        </td>
                        <td colspan="3">
                            <textarea cols="55" rows="7" name=txtdesc id=txtdesc onfocus="issue()"
                                        onkeypress="return noEnter(event)"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdH" colspan=4><center>
                            <input type=submit value=Submit onclick="this.style.cursor='wait';">
                            <input type=reset value="Clear All">
                            <input type=button value=Exit onclick="closeWindow()">
                            </center>
                        </td>
                    </tr>
                </table>
            </td>
         </tr>   
    </table>
  </form>
  
  </body>
</html>