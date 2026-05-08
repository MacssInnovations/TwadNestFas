<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page  contentType="text/html;charset=windows-1252"%>
<%@ page session="false" import="java.util.*,java.sql.*,Servlets.Security.classes.UserProfile" %>
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

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
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
    <title>Contractor_Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/ContractorDetailsReportJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
      <!--div.scroll {	
      height: 100px;	
      width: 100%;	
      overflow: auto;	
      border: 1px solid #666;	
      background-color: #fff;	
      padding: 0px;
     visibility: hidden;
     position: relative;
      }-->
      
    </style>
  </head>
  <body>
  <form name="ContractorDetailsForm" action="../../../../../ContractorDetailsReport" method="Post">
  <%
  HttpSession session=request.getSession(false);
  UserProfile empprofile=(UserProfile)session.getAttribute("UserProfile");
  System.out.println("user id:"+empprofile.getEmployeeId());
  int empid=empprofile.getEmployeeId();
  int oid=0;
  String oname="";
  try
    {
        ps=connection.prepareStatement("select office_id,office_name from com_mst_offices where office_id=(select office_id from hrm_emp_current_posting where employee_id=?)");
        ps.setInt(1,empid);
        results=ps.executeQuery();
        if(results.next())
            {
            oid=results.getInt("office_id");
            oname=results.getString("office_name");
            System.out.println(oid);
            }
        results.close();
        
    }
    catch(Exception e)
        {
        System.out.println(e);
        }
  %>
  <table cellspacing="2" cellpadding="3" border="1" width="100%">
  <tr class="tdH">
            <td colspan="2" align="center"><b>Contractor Details</b></td>
        </tr>
    <tr class="table">
        <td>Office Id</td>
        <td><select name="cmbcontractorffice" id="cmbcontractorffice" value="select Office">
            <option value="<%=oid%>"> <%=oname%> </option>
        </select></td>
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
          
    <tr>
        <td colspan="2" align="center"><input type="submit" value="submit"></input></td>
    </tr>
  </table>
  </form>
  </body>
</html>