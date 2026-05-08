<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Edit Response</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../scripts/EditResponse.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
          </script> 
  </head>
  <body class="table">
  
  <%
  Connection connection=null;
   Statement statement=null;
   PreparedStatement ps=null;
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
   // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

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

  
  <form action="../../../CreateServletResponse.con" name="frmHelp" onsubmit="return nullcheck();" method="post">
    <table width="100%" >
        <tr>
            <td class="tdH"><center><b>Edit Response</b></center></td>
        </tr>
          <tr>
            <td>
                <table  cellspacing="3" cellpadding="1" width="100%">
                    <tr>
                        <td colspan="1">
                            Issue_Id
                        </td>
                        <td>
                         <input type="text" name="txt_iss_id" id="txt_iss_id" maxlength="5" size="5" onkeypress="return  numbersonly1(event,this)" onblur="displaydetails()"/>
                            <select name="cmbissue_id" id="cmbissue_id">
                            <option value=0>--Select Issue Id--</option>
                            <%
                                int issueid=0;
                                ps=connection.prepareStatement("select ISSUE_ID from HLP_ISSUE_REQUESTS where ISSUE_STATUS = 'O' and ISSUE_SOLUTION is not null order by Issue_id");
                                results=ps.executeQuery();
                                while(results.next())
                                {
                            %>
                                <option value="<%=results.getInt("issue_id")%>"><%=results.getInt("issue_id")%></option>
                            <%
                                    
                                }
                            %>
                            </select>
                        </td>
                        
                         <td>Office Name</td>
                        <td><input type="text" name="txtofficename" size="40" disabled readonly >
                        </td>
                    </tr>
                    <tr>
                        <td>
                            UserName:
                        </td>
                        
                        <td>
                            <input type=text name=txtUserName size=30  disabled >
                            <input type=hidden name=txtHUserName  >
                        </td>
                    <%
                       /* }catch(Exception e)
                        {
                            System.out.println("Exception in Catch:"+e);
                        }*/
                    %>
                        <td>
                        Major System:
                        </td>
                        <td>
                        <input type=text name=txtMajor size=45 disabled>
                        <!--<select name="cmbMajor" id="cmbMajor" onchange="minor()" disabled>
                                    <option value=0>--Select Major System--</option>
                                    <%
                                        try{
                                        
                                        PreparedStatement ps1=connection.prepareStatement("select major_system_id,major_system_desc from sec_mst_major_systems");
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
                                    </select>-->
                        </td>
                        
                    </tr>
                    <tr>
                        
                        <td>
                            Minor System:
                        </td>
                        <td>
                        <input type=text name=txtMinor size="45" disabled>
                            <!--<select name="cmbMinor" id="cmbMinor" disabled>
                                         <option value=0>--Select Minor System--</option>
                                         </select>-->
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Priority:
                        </td>
                        <td >
                        <input type=text name=txtpriority size="30" disabled>
                            <!--<select name="cmbPriority" id="cmbPriority" disabled>
                            <option value="High">High</option>
                            <option value="Medium">Medium</option>
                            <option value="Low">Low</option>
                            </select>-->
                        </td>
                        <td >
                            Requested Date:
                        </td>
                        <td >
                            <input type=text name=txtdate size=30 disabled>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdH" colspan="4"><b>Problem Description:</b>
                        </td>
                    </tr>
                    <tr>
                        <td >Subject:
                        </td>
                        <td colspan="3">
                            <input type=text name=txtSubject size=60 disabled>
                        </td>
                    </tr>
                    <tr>
                        <td >
                            Description:
                        </td>
                        <td colspan="3">
                            <textarea cols="55" rows="7" name=txtdesc id=txtdesc readonly="readonly"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdH" colspan="4"><b>Solution Description:</b>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Solution:<label style="color:rgb(255,0,0);">*</label>
                        </td>
                        <td>
                            <textarea rows="5" cols="55" name=txtsolution onfocus="issue()"></textarea>
                        </td>
                        </tr>
                        <tr>
                        <td>
                            Status:<label style="color:rgb(255,0,0);">*</label>
                        </td>                       
                        <td>
                            <select name=cmbstatus id=cmbstatus>
                            <option value="0">--Select Status--</option>
                            <option value="O">Open</option>
                            <option value="C">Closed</option>
                            </select>
                            <!--<input type=text name=status size=30 disabled>-->
                        </td>
                        </tr>
                    
                    <tr>
                        <td class="tdH" colspan=4><center>
                            <input type=submit value=Submit name=submit>
                            <input type=reset value="Clear All" onclick="funclear()">
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