<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Standard Test Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/StandardTest.js"></script>
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
                   
                // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
					ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
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
    
  <form action="" name="StandardForm">
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Standard Test</b></td>
                   
       </tr> 
        <tr>
            <td class="table">Standard Test Code</td>
            <td class="table">
            <input type="text" name="txtStanId" size="5" 
                   id="txtStanId"/>
            </td>
        </tr>
        <tr>
            <td class="table">Sample Type Code</td>
          <td class="table">
                    <select id="txtMainId" name="txtMainId" maxlength="5">
           <%
      
          try
          {
              
            String sql="select * from WQS_MST_SAMPLE_TYPES ORDER BY SAMPLE_TYPE_ID";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strCusId=results.getString("SAMPLE_TYPE_ID");
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
            <td class="table">Test Purpose Id</td>
          <td class="table">
                    <select id="txtPurposeId" name="txtPurposeId" maxlength="5">
           <%
          try
          {
              
            String sql="select distinct TEST_PURPOSE_ID from WQS_MST_TEST_PURPOSE ORDER BY TEST_PURPOSE_ID";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strPurposeId=results.getString("TEST_PURPOSE_ID");
	        //String strCusDesc=results.getString("CUSTOMER_TYPE_DESC");
               
                out.println("<option value='" + strPurposeId + "'>"+strPurposeId+"</option>");   
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
            <td class="table">Element Symbol</td>
            <td class="table">
            <input type="text" name="txtElement" size="30" 
                   id="txtElement"/>
            </td>
        </tr>
        
        <tr>
            <td class="table">Test Name</td>
            <td class="table">
            <input type="text" name="txtTestName" size="60"
        id="txtTestName"/>
            </td>
        </tr> 
        
        <tr>
            <td class="table">Test Nature Id</td>
          <td class="table">
                    <select id="txtTestType" name="txtTestType" maxlength="5">
           <%
      
          try
          {
              
            String sql="select distinct TEST_NATURE_ID from WQS_MST_TEST_NATURE ORDER BY TEST_NATURE_ID";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strNatureId=results.getString("TEST_NATURE_ID");
	        out.println("<option value='" + strNatureId + "'>"+strNatureId+"</option>");   
                
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
            <td class="table">CPHE Standard</td>
            <td class="table">
            <input type="text" name="txtCphe" size="15"
        id="txtCphe"/>
            </td>
        </tr>      
         <tr>
            <td class="table">BIS Standard</td>
            <td class="table">
            <input type="text" name="txtBis" size="15"
        id="txtBis"/>
            </td>
        </tr>     
         <tr>
            <td class="table">WHO Standard</td>
            <td class="table">
            <input type="text" name="txtWho" size="15"
        id="txtWho"/>
                    </td>
        </tr>     
         <tr>
            <td class="table">Practical Standard</td>
            <td class="table">
            <input type="text" name="txtPrac" size="15"
        id="txtPrac"/>
            </td>
        </tr>     
         <tr>
            <td class="table">Print Priority</td>
            <td class="table">
            <input type="text" name="txtPrint" size="10"
        id="txtPrint"/>
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
            Standard Test Code
          </th>
          <th>
            Sample Type Code
          </th>
         <th>
            Test Purpose Code
          </th>
         
          <th>
            Element Symbol
          </th>
          <th>
            Test Name
          </th>
         <th>
            Test Type
          </th>
          <th>
            CPHE Standard
          </th>
          <th>
            BIS Standard
          </th>
          <th>
            WHO Standard
          </th>
          <th>
            Practical Standard
          </th>
          <th>
          Print Priority
          </th>
                             
          </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
    
  </form>
  
  </body>
</html>