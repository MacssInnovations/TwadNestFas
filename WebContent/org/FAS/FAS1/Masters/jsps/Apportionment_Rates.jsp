<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Master Asset Class Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/Apportionment_Rates.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body onload="callServer('Get','null');" class="table">
  <%
  Connection connection=null;
  Statement statement=null;
  PreparedStatement ps = null;
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
                   
                 //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
  
  
  <form action="" name="frmApportGrantRates">
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="3" class="tdH" align="center"><b>Apportionment of Grant Rates (Financial Year-wise)</b></td>
                   
       </tr> 

        <tr>
            <td class="table">Financial Year</td>
          <td class="table">
          	<select name="cmbFinYear" id="cmbFinYear" onchange="callServer('Get','null');" >
          	
          		<%
          		
    		//	try
               // {
	           //     ps=connection.prepareStatement("select FINANCIAL_YEAR from CASH_BOOK_CONTROL");
	            //    results=ps.executeQuery();
	            //    while(results.next())
	            //    {
	            //    	out.println("<option value = "+results.getString("FINANCIAL_YEAR")+">"+
	             //   			results.getString("FINANCIAL_YEAR")+"</option>");
	              //  }
         		//}catch(Exception e)
            	//{
         		//	System.out.println("Exception in Select:"+e);
    	       // }

          		%>
          	</select>
           </td>
        </tr>

        <tr>
            <td class="table">Apportion of Grant Category Code</td>
          <td class="table">
            <select name="cmbApportGrantCatCode" id="cmbApportGrantCatCode">
          		<%
    			try
                {
	                ps=connection.prepareStatement("SELECT apportion_grant_cate_code, " +
							                		 "apportion_grant_category " +
							                		 "FROM fas_apport_category_mst");
	                results=ps.executeQuery();
	                while(results.next())
	                {
	                	out.println("<option value = "+results.getInt("apportion_grant_cate_code")+
	                    			">"+results.getString("apportion_grant_category")+"</option>");
	                }
         		}catch(Exception e)
            	{
         			System.out.println("Exception in Select:"+e);
    	        }
         		%>
            </select>
          </td>
           
        </tr>


        <tr>
            <td class="table">Rate of Apportionment</td>
            <td class="table">
	            <input maxlength=7 size=7 type="text" name="txtApportRate" id="txtApportRate" onkeypress="return numFloatInt(event,this)" onchange="checkRate(this);"/> %
            </td>
        </tr>

        <tr>
          <td colspan="3" class="table">
            <input type="button" name="CmdAdd" value="ADD" id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdUpdate" value="UPDATE"
                   id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdDelete" value="CANCEL"
                   id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdClear" value="CLEAR"
                   id="CmdClear" onclick="refresh()"/>
            <input type="button" name="CmdExit" value="EXIT" onclick="closeWindow()">
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
          	Apportion of Grant Category</th>
          <th>
          	Rate of Apportionment
          </th>
          <th>
          	Status
          </th>
        </tr>
        <tbody id="tblList" class="table">
        </tbody>
        </table>
    
  </form>
  </body>
</html>