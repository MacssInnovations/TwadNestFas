<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Selection of Employee</title>
    <script type="text/javascript" src="../scripts/JobPopuupJS.js">
    </script>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <body >
  
  <form name="HRM_JobSearch" id="HRM_JobSearch">
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
      <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="80%">
         <tr class="tdH" >
        <th align="center" colspan="2">
                SELECTION OF AN OFFICE
                </th>
           </tr>
        <tr class="tdH" >
        <th align="left" colspan="2">
                Office Criteria Page
                </th>
           </tr>
           
          <tr class="table">
            <td>
              <div align="left">
                Select Office Level
              </div></td>
            <td>
              <div align="left">
               Service Group<select name="cmbolevel" id="cmbolevel" onchange="getRegion()">
                <option value="">Select Office Level</option>
                        <%
           ResultSet rs=null;
           try
           {
           ps=connection.prepareStatement("select OFFICE_LEVEL_ID,OFFICE_LEVEL_NAME from COM_MST_OFFICE_LEVELS  order by OFFICE_LEVEL_NAME");
            rs=ps.executeQuery();
             String strcode="";
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getString("OFFICE_LEVEL_ID");
                strname=rs.getString("OFFICE_LEVEL_NAME");
                
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
               </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Select the Office
              </div></td>
            <td>
              <div align="left">
                  <table >
                      <tr>
                          <td><div id="divregion" style="visibility:hidden">Region</div></td>
                          <td><div id="divcircle" style="visibility:hidden">Circle</div></td>
                          <td><div id="divdivision" style="visibility:hidden">Division</div></td>
                          <td><div id="divsubdiv" style="visibility:hidden">Sub Division</div></td>
                          <td><div id="divsection" style="visibility:hidden">Section</div></td>
                      </tr>
                      <tr>
                          <td><select name="cmbregion" id="cmbregion" style="visibility:hidden"   onchange="getCircle()"></select></td>
                          <td><select name="cmbcircle" id="cmbcircle" style="visibility:hidden" onchange="getDivision()"></select></td>
                          <td><select name="cmbdivision" id="cmbdivision" style="visibility:hidden" onchange="getSubDivision()" ></select></td>
                          <td><select name="cmbsubdiv" id="cmbsubdiv" style="visibility:hidden"  onchange="getSection()" ></select></td>
                          <td><select name="cmbsection" id="cmbsection" style="visibility:hidden"  onchange="getSection1()" ></select></td>
                      </tr>
                  </table>
                </div>
            </td>
          </tr>
          <tr class="table">
            <td colspan="2">
              <div align="center">
                 </div></td>
          </tr>
        </table>
      
       
        
        
      </div>
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="80%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
      <input type="button" id="cmdsubmit" name="Submit" value="Submit" onclick="btnsubmit()">
       <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="btncancel()">
     
      </div>
      </td>
      </tr>
      
      </table>
    
    </form></body>
</html>