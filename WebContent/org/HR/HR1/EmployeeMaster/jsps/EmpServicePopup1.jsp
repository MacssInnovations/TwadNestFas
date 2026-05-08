<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Selection of Employee</title>
    <script type="text/javascript" src="../scripts/EmpServicePopupJS1.js">
    </script>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <body >
  
  <form name="HRM_EmpSearch" id="HRM_EmpSearch">
      <p>
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
      </p>
      <p>
        &nbsp;
      </p>
     
      <table  border="0" width="80%">
      <tr><td>
       <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="100%">
         <tr class="tdH" >
        <th align="center" colspan="2">
                SELECTION OF AN EMPLOYEE
                </th>
           </tr>
        <tr class="tdH" >
        <th align="left" colspan="2">
                Employee Search Criteria Page
                </th>
           </tr>
           
          <tr class="table">
            <td>
              <div align="left">
                Employee Name
              </div></td>
            <td>
              <div align="left">
                <input type="text" name="txtEmpName" id="txtEmpName" maxlength="40" size="40"      /> (Type few Starting letters)
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Designation
              </div></td>
            <td>
              <div align="left">
              <table cellspacing="2" cellpadding="3" border="0" width="100%">
                <tr>
                  <td>Service Group</td>
                  <td><div  id="divdes" style="visibility:hidden"> Designation </div></td>
                </tr>
                <tr>
                    <td>              
               
               <select name="cmbsgroup" id="cmbsgroup" onchange="getDesignation()">
                <option value="0">Select Service Group</option>
                        <%
           ResultSet rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_name from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_name");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_name");
                
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
               </td>
               <td>
              <select name="cmbdes" id="cmbdes" style="visibility:hidden"></select>
              </td>
            </tr>
        </table>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td colspan="2">
              <div align="center">
                <input type="button" name="cmdgo" value="Go" id="cmdgo" onclick="getEmployee()"/>
                 </div></td>
          </tr>
        </table>
      
       
        
         <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th>
              Select
            </th>
            <th>
             Emp Code
            </th>
            <th>
             Employee Name
            </th>
            <th>
             Initial
            </th>
            <th>
             Designation 
            </th>
           
            <th>
             Date of Birth
            </th>
            <th>
             GPR No
            </th>
          </tr>
          <tbody id="tb" class="table">
          
         
          </tbody>
        </table>
     
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
      <input type="button" id="cmdsubmit" name="Submit" value="Submit" onclick="btnsubmit()">
         <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
      </table>
       </div>
    </td></tr></table>
    </form></body>
</html>