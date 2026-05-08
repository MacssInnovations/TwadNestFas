<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.text.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="../scripts/DailyScript.js"></script>
    <link href="../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <title></title>
  </head>
  <body><%
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
    }
    catch(SQLException e)
    {
    }
  }
  catch(Exception e)
  {
  }
  %><form name="StatusReport" id="StatusReport">
      <table cellspacing="2" cellpadding="3" border="1" width="75%"
             align="center" class="table">
        <tr>
          <td colspan="2" class="tdH">
            <div align="center">
              <strong>Daily Status Details</strong>
            </div>
          </td>
        </tr>
        <tr>
          <td class="tdH" colspan="2">
            <label>
              <b>User Details</b>
            </label>
          </td>
        </tr>
        <tr>
          <%
                        HttpSession session=request.getSession(false);
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
                         ps=connection.prepareStatement("select employee_name||decode(employee_initial,null,' ','.'||employee_initial) employee_name from hrm_mst_employees where employee_id=?");
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
                     
          <td>Employee ID :</td>
          <td>
            <input type="text" name="txtUserName" size="30"
                   value="<%if(Emp_Id!=0){%><%=Emp_Id%> <%}%>"
                   disabled="disabled"></input>
          </td>
        </tr>
        <tr>
          <td>UserName :</td>
          <td>
            <input type="text" name="txtUserName" size="30"
                   value='<%if(empname!="null"){%><%=empname%> <%}%>'
                   disabled="disabled"></input>
            <input type="hidden" name="txtHUserName"
                   value='<%if(empname!="null") {%><%=empname%> <%}%>'></input>
          </td>
        </tr>
        <%
                        }catch(Exception e)
                        {
                            System.out.println("Exception in Catch:"+e);
                        }
                    %>
        <tr>
          <td class="tdH" colspan="2">
            <label>
              <b>Status Details</b>
            </label>
          </td>
        </tr>
        <tr>
          <td class="table">Report Date</td>
          <td class="table">
            <input type="TEXT" name="reportdate" id="reportdate"
                   readonly="readonly"/>
          </td>
        </tr>
        <tr>
          <td class="table">Activities Carried Out</td>
          <td class="table">
            <!-- <input type="text" name="aco" size="40" maxlength="25" id="aco"/>-->
            <textarea cols="25" name="aco" id="aco" rows="5"></textarea>
          </td>
        </tr>
        <tr>
          <td align="center" colspan="2" class="table" id="add"
              style="display:none">
            <input type="button" name="CmdAdd" value="SAVE" id="CmdAdd"
                   onclick="callServer('Add','null')"/>
            <input type="button" name="CmdClear" value="CLEAR ALL" id="CmdClear"
                   onclick="clearAll()"/>
          </td>
          <td colspan="2" align="center" class="table" id="update"
              style="display:block">
            <input type="button" name="CmdUpdate" value="UPDATE" id="CmdUpdate"
                   onclick="callServer('Update','null')"/>
            <input type="button" name="CmdDelete" value="DELETE" id="CmdDelete"
                   onclick="callServer('Delete','null')"/>
            <input type="button" name="CmdClear" value="CLEAR ALL" id="CmdClear"
                   onclick="clearAll()"/>
          </td>
        </tr>
      </table>
      <table cellspacing="3" cellpadding="2" border="1" width="75%"
             align="center">
        <tr>
          <td class="table">Existing Details</td>
        </tr>
      </table>
      <table id="Existing" cellspacing="2" cellpadding="3" border="1"
             width="75%" align="center">
        <tr class="tdH">
          <th>Select</th>
          <th>Report Date</th>
          <th>Activities Carried Out</th>
        </tr>
        <tbody id="tblList" class="table">
          <%
      
          try
          {
              
            String sql="select * from com_mst_daily_status ORDER BY DATE_OF_REPORT";
            //out.println("hai");
             results=statement.executeQuery(sql);
             
            try
            {
              while(results.next())
              {
                int projectLeaderId=results.getInt("PROJECT_LEADER_ID");
                java.util.Date dateOfReport=results.getDate("DATE_OF_REPORT");
                DateFormat formatter ; 
     //Date date ;    
              formatter = new SimpleDateFormat("dd/MM/yyyy");
             // date = (Date)formatter.parse("11-June-07");    
              String s = formatter.format(dateOfReport);
	        String activityCarriedOut=results.getString("ACTIVITIES_CARRIED_OUT");
                            if(activityCarriedOut==null)
                                       {
                                           activityCarriedOut="Undefined Record Found";
                                       }
                                       else {
                                         //  activityCarriedOut=activityCarriedOut;
                                           
                                       } 
                 //  String id=projectLeaderId+dateOfReport; 
                 System.out.println("The id"+activityCarriedOut+s);
                out.println("<tr id='" +s+"'>");   
                out.println("<td><a href=\"javascript:loadValuesFromTable('" +s+"')\">Edit</a></td>");
                out.println("<td>" + s + "</td>");
                out.println("<td>" + activityCarriedOut + "</td></tr>");
                 
              }
            }
            catch(SQLException e)
            {
              System.out.println("Exception in resultset:"+e);
            }
            finally
            {
              results.close();
            }
          }
          
      
      catch(SQLException e)
      { 
        System.out.println("Exception :"+e);
      }
    %>
        </tbody>
      </table>
      <table id="Exit" cellspacing="2" cellpadding="3" border="1" width="75%"
             align="center">
        <tr>
          <td class="tdH">
            <div align="center">
              <input type="button" name="CmdExit" value="EXIT" id="CmdExit"
                     onclick="Exit()" align="middle"/>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>