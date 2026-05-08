<%@ page  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
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
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    
      
            
    
    <link href="../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <link href="../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
          <script type="text/javascript" src="../scripts/AjaxDailyStatusReport.js"></script> 
          <script type="text/javascript"  src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script><title>Daily Status Report</title>
  </head>
  <body class="table">
  
  <form name="daily_status_report" action="../../../daily_status_update">
  <table width="100%" >
        <tr>
            <td class="tdH"><center><b>Daily Status Report</b></center></td>
        </tr>
          <tr>
            <td class="tdH"><label><b>User Details</b></label></td>
            </tr><tr><td>
                <table cellspacing="1" cellpadding="3" width="100%" style="border-color:rgb(185,201,202); border-width:medium; border-style:groove;">
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
                            <input type=text name=txtUserName size=30 value=<%if(Emp_Id!=0){%>"<%=Emp_Id%>" <%}%> disabled >
                                                       
                        </td>
                        </tr>
                        <tr>
                        <td>
                            UserName :
                        </td>
                        <td>
                            <input type=text name=txtUserName size=30 value=<%if(empname!="null"){%>"<%=empname%>" <%}%> disabled >
                            <input type=hidden name=txtHUserName value=<%if(empname!="null") {%>"<%=empname%>" <%}%> >
                            
                        </td>
                       </table></td></tr>
                    <%
                        }catch(Exception e)
                        {
                            System.out.println("Exception in Catch:"+e);
                        }
                    %>
                    <tr>
            <td class="tdH" align="left"><b>Status Report</b></td>
        </tr>
                    <tr><td>
                    <table cellspacing="1" cellpadding="3" width="100%" style="border-color:rgb(185,201,202); border-width:medium; border-style:groove;">
                       <tr> 
                        

    <td width="251" height="102">Date of Report :</td>
    <%
    try
    {
    String val=request.getParameter("val");
    if(val!=null && val.equalsIgnoreCase("yes"))
    {
        %>
        <script type="text/javascript">
        alert('Successfully inserted');
        </script>
        <%
    }
    else if(val!=null)
    {
    %>
        <script type="text/javascript">
        alert('<%=val%>');
        </script>
        <%
    
    }
    
    Statement st=connection.createStatement();
    ResultSet rs=st.executeQuery("select to_char(now(),'dd/mm/yyyy') from dual");
    rs.next();
    %>
    <td><input type="text" id="date_of_report"  name="date_of_report" size="10" maxlength="10" value="<%=rs.getString(1)%>" 
                            onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>                   
                           <img src="../../../images/calendr3.gif"  onclick="showCalendarControl(document.daily_status_report.date_of_report);" alt="Show Calendar"></img></td></tr>
    <%
    }
    catch(Exception e)
    {
    }
    %>
    
    <!--<input type="text" name="txtClosureDate" size="10" maxlength="10"
                           onFocus="return officeCheck();javascript:vDateType='3'" onBlur="check1();return checkdt(this);" onkeypress="return  calins(event,this)"/>                   
                           <img src="../../../../../images/calendr3.gif"  onclick="showCalendarControl(document.frmClosure.txtClosureDate);" alt="Show Calendar"></img>-->
 
 
  <tr>
    <td>Activities Carried out :</td>
    <td><textarea rows="7" cols="30" id="aco" name="activities_carried_out"></textarea>
    </td>
  </tr>
  <tr align="center"><td colspan="2"><input type="button" value="Insert" onclick="callServer(document.getElementById('date_of_report').value,document.getElementById('aco').value)" /><input type="button" value="Clear" onclick="document.getElementById('aco').value='';document.getElementById('date_of_report').value='';"><input type="button" value="Exit" onclick="javascript:window.close();"></td></tr>
  </table>
  </td></tr>
  </table>

</form>
  </body>
</html>