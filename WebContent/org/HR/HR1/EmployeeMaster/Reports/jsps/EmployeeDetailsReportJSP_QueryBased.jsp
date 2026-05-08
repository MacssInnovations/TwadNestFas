<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Edit Employee's Additional&nbsp;Details </title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/ValidationEmployee2.js"></script>
    <script type="text/javascript" src="../scripts/EmployeeDetailsReportJS_QueryBased.js"></script>
    <style type="text/css">
          body 
          {
                background-color: #ffffff; 
          }
          a:link { color: #002173; }
          
          div.scroll
          {	
              height: 100px;	
              width: 100%;	
              overflow: auto;	
              border: 1px solid #666;	
              background-color: #fff;	
              padding: 0px;
             visibility: hidden;
             position: relative;
          }
      
    </style>
  </head>
  <body>
  <form name="frmEmployee" method="GET" class="table">
  <%
          Connection connection=null;
          PreparedStatement ps=null;
          Statement statement=null;
          ResultSet results=null;
          ResultSet results1=null;
          ResultSet results2=null;
          int strNativeDistrict=0;
          int strNativeTaluk=0;
          String strEmpStatus="";
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
  
  <!-- OFFICE DETAILS -->
  <% 
          HttpSession session=request.getSession(false);
          UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");

          int empid=empProfile.getEmployeeId();
          //int empid=11268;
          int  oid=0;
          try
          {
                   
                    ps=connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                    ps.setInt(1,empid);
                    results=ps.executeQuery();
                    if(results.next()) 
                    {
                        oid=results.getInt("OFFICE_ID");
                     
                    }
                    results.close();
                    ps.close();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
   %>
   <script language="javascript" type="text/javascript">
                   OfficeId="<%=oid%>";
                  // alert(OfficeId);
    </script>
    <table border="2" width="100%">
      <tr>
            <td colspan="2" align="left" valign="top" class="table" width="60%">
            <table border="0" width="100%">
                <tr>
                  <td colspan='2' class="tdH" align="center"><strong> Query Based on Employee Details</strong></td>
                </tr>
                
                <tr>
                  <td colspan="2" class="tdH">Designation</td>
                </tr>
                <tr>
                    <td>
                     <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                    Service Group
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <select name="servicegroup" id="servicegroup" style="width:45%"
                                          onclick="loadServiceGroup()">
                                    <option>Select Service Group</option>
                                  </select>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                  <div class="scroll" style="width:45%" id="divframegroup">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                        </table>
                     </td>
                     <td>
                     <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                    Designation
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <select name="designation" id="designation" style="width:45%"
                                          onclick="loadDesignation()" disabled="disabled">
                                    <option>Select Designation</option>
                                  </select>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                  <div class="scroll" style="width:45%" id="divframedesign">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                        </table>
                     </td>
                </tr>
            </table>
            </td>
      </tr>
      </table>
      <table border="2" width="100%">
      <tr>
            <td colspan="2" align="left" valign="top" class="table" width="100%">
            <table cellspacing="2" cellpadding="3" border="0" width="100%">
                  <tr>
                        <td>
                            Community
                        </td>
                        <td>
                              <select name="Community" id="Community">
                                    <option value="">--Select Community--</option>
                                      <%
                                        try
                                        {
                                          ps=connection.prepareStatement("Select * from HRM_MST_COMMUNITY");
                                          results=ps.executeQuery();
                                          while(results.next())
                                          {
                                              out.println("<option value='" + results.getString("Community_Code") + "'>" + results.getString("Community_Name") +"</option>");
                                              
                                          }
                                          out.println("<option value='All'>All</option>");
                                          }
                                          catch(Exception e){System.out.println("error in the community code" + e);};
                                      %>
                              </select>
                        </td>
                  </tr>
                  <tr>
                        <td>
                              Native&nbsp;District
                        </td>
                        <td>
                              <select name="Native_District" id="Native_District">
                                    <option value="">--Select District--</option>
                                    <% 
                                              try
                                              {
                                                  results=ps.executeQuery("select * from COM_MST_DISTRICTS order by District_Name");
                                                  while(results.next())
                                                  {
                                                      String temp=results.getString("District_Code");
                                                      %>
                                                        <option value=<%=temp%>><%= results.getString("District_Name")%></option>
                                                      <% 
                                                  }
                                                  out.println("<option value='All'>All</option>");
                                                  results.close();
                                              }
                                              catch(SQLException e)
                                              {
                                                      System.out.println("Exception in districts:"+e);
                                              }
                                %>        
                              </select>
                        </td>
                  </tr>
                  <tr>
                        <td>
                              Qualification
                        </td>
                        <td>
                              <input type="text"
                                     name="Qualification"
                                     maxlength="60"
                                     size="60"/>
                        </td>
                  </tr>
                  <tr>
                        <td>
                              Specialisation
                        </td>
                        <td>
                              <input type="text"
                                     name="Specialisation"
                                     maxlength="50"
                                     size="50"/>
                        </td>
                  </tr>
                </table>
                </td>
      </tr>
      <tr>
            <td colspan="4" class="tdH"
                align="right">
                  <input type="button"
                         value="Submit" name="cmdSubmit" id="cmdSubmit" onclick=" callFunction()"></input>
                  &nbsp;
                  <input type="Button"
                         value=" Cancel "
                         name="cmdCancel"
                         onclick="self.close();"></input>
            </td>
      </tr>
    </table>
   </form>
  </body>
</html>