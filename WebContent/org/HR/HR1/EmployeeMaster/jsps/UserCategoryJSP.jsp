<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>USER CATEGORY</title>
    <script type="text/javascript" src="../scripts/UserCategoryJS.js">
    </script>
   
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <body>
  
  <form name="frmusercategory" id="frmusercategory">
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
        
        <tr class="tdH">
        <td colspan="2">
      <div align="center">
              <strong>
                User Category</strong>
             </div>
            </td>
           </tr>
           
          <tr class="table">
            <td>
              <div align="left">
                User Category Id
              </div></td>
            <td>
              <div align="left">
                <input type="text" name="txtusercategoryid" id="txtusercategoryid" maxlength="2" size="2" readonly="readonly"  style="background-color: #ececec"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                User Category
              </div></td>
            <td>
              <div align="left">
                <input type="text" name="txtusercategory" maxlength="40" size="40"
                       id="txtusercategory" style="TEXT-TRANSFORM:UPPERCASE"  />
              </div>
            </td>
          </tr>
          <tr class="table">
            <td colspan="2">
              <div align="center">
                <input type="button" name="cmdadd" value="ADD" id="cmdadd" onclick="doFunction('Add','null')"/>
                <input type="button" name="cmdupdate" value="UPDATE" id="cmdupdate" onclick="doFunction('Update','null')" disabled/>
                <input type="button" name="cmddelete" value="DELETE" id="cmddelete" onclick="doFunction('Delete','null')" disabled/>
                <input type="button" name="cmdclear" value="CLEAR ALL"  id="cmdclear" onclick="doFunction('Clear','null')"/>
            
              </div></td>
          </tr>
        </table>
      
       
        
         <table id="mytable" align="center"  cellspacing="3" cellpadding="2" border="1" width="80%">
          <tr class="tdH">
            <th>
              Select
            </th>
            <th>
              User Category Id
            </th>
            <th>
             User Category
            </th>
          </tr>
          <tbody id="tb" class="table" align="left">
          
          <%
          ResultSet rs=null;
           try
           {
            ps=connection.prepareStatement("select USER_CATEGORY_ID,USER_CATEGORY_DESC from SEC_MST_USER_CATEGORY order by USER_CATEGORY_ID");
            rs=ps.executeQuery();
                       
            while(rs.next())
            {
               
                int strcode=rs.getInt("USER_CATEGORY_ID");
                
                String strname=rs.getString("USER_CATEGORY_DESC");
              
                out.println("<tr id='" + strcode + "'>");   
                out.println("<td><a href=\"javascript:loadTable('" + strcode + "')\">Edit</a></td>");
                out.println("<td>"+strcode+"</td>");
                out.println("<td>"+strname+"</td></tr>");
                
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
                connection.close();
          }
          %>
          </tbody>
        </table>
      </div>
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="80%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
      <input type="button" id="Exit" name="Exit" value="Exit" onclick="toExit()">
      </div>
      </td>
      </tr>
      
      </table>
     
    </form></body>
</html>