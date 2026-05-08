<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Supplier Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/suppliermaster.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <!--onload="callServer('Get','null')"-->
  <body onload="callServer('Get','null')" class="table">
  <%
  Connection connection=null;
  Statement statement=null;
  ResultSet results=null;
  ResultSet results1=null;
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
    }
    catch(SQLException e)
    {
    }
  }
  catch(Exception e)
  {
  }
  %>
    
  <form action="" name="SupplierForm">
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Supplier Master</b></td>
                   
       </tr> 
        
        
        <tr>
            <td class="table">Category Code</td>
          <td class="table">
                    <select id="txtCatId" name="txtCatId" maxlength="5">
           <%
      
          try
          {
              
            String sql="select * from WQS_MST_INV_CATEGORY ORDER BY CATEGORY_CODE";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strCusId=results.getString("CATEGORY_CODE");
	        //String strCusDesc=results.getString("CUSTOMER_TYPE_DESC");
               
                out.println("<option value='" + strCusId + "'>"+strCusId+"</option>");   
                //out.println("<td><a href=\"javascript:loadValuesFromTable('" + strCusId + "')\">Edit</a></td>");
                //out.println("<td>" + strCusId + "</td>");
	// out.println("<td>" + strCusDesc + "</td></tr>");
                 
              }
            }
            catch(Exception e)
            {
              System.out.println("Exception in resultset:"+e);
            }
  %>   
            
            
            </select>
           </td>
         
           
           
        </tr>
         <tr>
            <td class="table">Supplier Code</td>
            <td class="table">
            <input type="text" name="txtSupId" size="5" 
                   id="txtSupId"/>
            </td>
        </tr>
        
        <tr>
            <td class="table">Supplier Name</td>
            <td class="table">
            <input type="text" name="txtSupName" size="50"
        id="txtSupName"/>
            </td>
        </tr>      
              
        <tr>
            <td class="table">Address1</td>
            <td class="table">
            <input type="text" name="txtAddr1" size="50"
        id="txtAddr1"/>
            </td>
        </tr>  
        <tr>
            <td class="table">Address2</td>
            <td class="table">
            <input type="text" name="txtAddr2" size="50"
        id="txtAddr2"/>
            </td>
        </tr>  
        <tr>
            <td class="table">Address3</td>
            <td class="table">
            <input type="text" name="txtAddr3" size="50"
        id="txtAddr3"/>
            </td>
        </tr>  
        <tr>
            <td class="table">Pin Code</td>
            <td class="table">
            <input type="text" name="txtPin" size="6"
        id="txtPin"/>
            </td>
        </tr>  
        <tr>
            <td class="table">Contact Phone1</td>
            <td class="table">
            <input type="text" name="txtPhone1" size="20"
        id="txtPhone1"/>
            </td>
        </tr>  
        <tr>
            <td class="table">Contact Phone21</td>
            <td class="table">
            <input type="text" name="txtPhone2" size="20"
        id="txtPhone2"/>
            </td>
        </tr>  
        
         <tr>
            <td class="table">Fax</td>
            <td class="table">
            <input type="text" name="txtFax" size="20"
        id="txtFax"/>
            </td>
        </tr>  
         <tr>
            <td class="table">E Mail</td>
            <td class="table">
            <input type="text" name="txtMail" size="25"
        id="txtMail"/>
            </td>
        </tr>  
        <tr>
          <td colspan="2" class="table">
            <input type="button" name="CmdAdd" value=" ADD " id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdDelete" value="DELETE"
                   id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdClear" value="CLEAR ALL"
                   id="CmdClear" onclick="clearAll()"/>
            <input type="button" name="Exit" value="EXIT" onclick="closeWindow()">
          </td>
        </tr>
    </table>
    <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center" >
        <tr>
          <td class="table"><b>Existing Details</b></td>
        </tr>
      </table>
      <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr class="tdH">
          <th>
            Select
          </th>
          <th>
            Category Code
          </th>
          <th>
            Supplier Code
          </th>
         <th>
            Supplier Name
          </th>
         
          <th>
            Address1
          </th>
          <th>
            Address2
          </th>
          <th>
            Address3
          </th>
          <th>
          Pin Code
          </th>
          
          <th>
          Contact Phone1
          </th>
          <th>
          Contact Phone2
          </th>
          <th>
          Fax
          </th>
          <th>
          E Mail
          </th>
          </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
    
  </form>
  
  </body>
</html>