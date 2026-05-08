<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <script type="text/javascript"            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>LISTING ALL HRE_EMPLOYEE_SERVICE_DETAILS</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
  </head>
  <body ><form name="HRE_EmployeeServiceDetails"
                                                                      id="HRE_EmployeeServiceDetails">
      <p>
        <%
  
  Connection connection=null;
  PreparedStatement ps=null;
  
  
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
      </p>
      <p>&nbsp;</p>
      <div align="center">
        <table width="100%">
          <tr class="tdH">
            <th align="center" colspan="11">EMPLOYEE SERVICE DETAILS</th>
          </tr>
          <tr>
            <td>
              <table id="mytable" align="center" cellspacing="3" cellpadding="2"
                     border="1" width="100%">
                <tr class="tdH">
                  <th>Service&nbsp;No </th>
                  <th>Date&nbsp;&nbsp;From</th>
                  <th>Date&nbsp;From Session</th>
                  <th>Date&nbsp;To </th>
                  <th>Date&nbsp;To Session</th>
                  <th>Designation </th>
                  <th>Office&nbsp;Dept </th>
                  <th>Office&nbsp;Name </th>
                  <th>Emp&nbsp;Status&nbsp;Desc </th>
                  <th>Status&nbsp;Detail </th>
                  <th>Remarks </th>
                </tr>
                <tbody id="tb" class="table">
                  <%
          
          ResultSet rs=null;
              try
             {
             int id=Integer.parseInt(request.getParameter("id"));
            
                     String sql="";
                     sql="select HRM_EMP_SERVICE_DATA.SERVICE_LIST_SLNO,HRM_EMP_SERVICE_DATA.DATE_FROM,HRM_EMP_SERVICE_DATA.DATE_FROM_SESSION,";
                     sql+="HRM_EMP_SERVICE_DATA.DATE_TO,HRM_EMP_SERVICE_DATA.DATE_TO_SESSION,HRM_EMP_SERVICE_DATA.DESIGNATION_ID,HRM_EMP_SERVICE_DATA.OFFICE_ID,";
                     sql+="HRM_EMP_SERVICE_DATA.EMPLOYEE_STATUS_ID,HRM_EMP_SERVICE_DATA.STATUS_DETAIL,HRM_EMP_SERVICE_DATA.REMARKS,";
                     sql+="HRM_MST_DESIGNATIONS.DESIGNATION,HRM_EMP_SERVICE_DATA.OFFICE_DEPT_ID,COM_MST_OFFICES.OFFICE_NAME,HRM_MST_EMPLOYEE_STATUS.EMPLOYEE_STATUS_DESC  ";
                     sql=sql+" from HRM_EMP_SERVICE_DATA ,HRM_MST_DESIGNATIONS,COM_MST_OFFICES,HRM_MST_EMPLOYEE_STATUS    where HRM_EMP_SERVICE_DATA.EMPLOYEE_ID=? and COM_MST_OFFICES.OFFICE_ID=HRM_EMP_SERVICE_DATA.OFFICE_ID and HRM_MST_EMPLOYEE_STATUS.EMPLOYEE_STATUS_ID=HRM_EMP_SERVICE_DATA.EMPLOYEE_STATUS_ID   and   HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_SERVICE_DATA.DESIGNATION_ID order by HRM_EMP_SERVICE_DATA.SERVICE_LIST_SLNO";
                     ps=connection.prepareStatement(sql);
                     ps.setInt(1,id);
                  rs=ps.executeQuery();
                 while(rs.next())           {
                 
  /*  EMPLOYEE_ID SERVICE_LIST_SLNO DATE_FROM DATE_FROM_SESSION DATE_TO 
    DATE_TO_SESSION DESIGNATION_ID OFFICE_ID EMPLOYEE_STATUS_ID STATUS_DETAIL REMARKS              
    */                   
                     out.println("<tr class='table'> ");
           out.println("<th>"+rs.getInt("SERVICE_LIST_SLNO")+" </th>");
             out.println("<th>"+rs.getDate("DATE_FROM")+"  </th>");
          out.println(" <th>"+rs.getString("DATE_FROM_SESSION")+"  </th>");
          out.println(" <th>"+rs.getDate("DATE_TO")+"  </th>");
          out.println(" <th>"+rs.getString("DATE_TO_SESSION")+"</th>");
          out.println(" <th>"+rs.getString("DESIGNATION")+" </th>");
          out.println(" <th>"+rs.getString("OFFICE_DEPT_ID")+" </th>");
          out.println(" <th>"+rs.getString("OFFICE_NAME")+"</th>");
          out.println(" <th>"+rs.getString("EMPLOYEE_STATUS_DESC")+"</th>");
          if(rs.getString("STATUS_DETAIL")!=null)
          out.println(" <th>"+rs.getString("STATUS_DETAIL")+" </th>");
          else
          out.println(" <th> </th>");
           if(rs.getString("REMARKS")!=null)
          out.println(" <th>"+rs.getString("REMARKS")+" </th>");
          else
          out.println(" <th> </th>");
          out.println(" </tr> ");
         
                  }
                 
                   
              }
                         
              catch(Exception e) {
             out.flush();
                  System.out.println("catch........"+e);
           
             }
          
          
          %>
                </tbody>
              </table>
              <table align="center" cellspacing="3" cellpadding="2" border="1"
                     width="100%">
                <tr class="tdH">
                  <td>
                    <div align="center">
                      <input type="button" id="Exit" name="Exit" value="Exit"
                             onclick="self.close()"></input>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>