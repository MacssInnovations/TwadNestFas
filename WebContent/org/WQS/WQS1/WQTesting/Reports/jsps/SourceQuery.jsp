<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Source Query Details</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    
    <!--<script type="text/javascript" src="../../scripts/CalendarControl.js"></script>-->
    <script type="text/javascript" src="../scripts/LabQuery.js"></script>
    <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
  </head>
  <body class="table">
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
  
  
  <!--<form action="../../../../sourcequery_servlet.view"   name="frmReport" method="post" onsubmit="return nullcheck()">-->
  <form action="SourceOutput.jsp" name="frmReport" method="post" onsubmit="return nullcheck()">
      <table border="1" cellspacing="2" cellpadding="1" width="100%">
                
        <tr>
          <td class="tdH">
            <center>
              <b>Source Wise Query</b>
            </center>
          </td>
        </tr>
        
        <tr>
          <td>
            <table border="1" cellspacing="2" cellpadding="1" width="100%">
              <tr>
                <td >From Date:</td>
                <td>
                  <input type="text" name="txtfromdate" id="txtfromdate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/>
                  <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmReport.txtfromdate);"
                       alt="Show Calendar" height="24" width="19"/>
                </td>
                </tr>
                <tr>
                <td>To Date:</td>
                <td>
                  <input type="text" name="txttodate" id="txttodate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"/>
                  <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmReport.txttodate);"
                       alt="Show Calendar" height="24" width="19"/>
                </td>
              </tr>
              
              <tr>
            <td class="table">Lab Code</td>
          <td class="table">
                    <select id="txtLabCode" name="txtLabCode" maxlength="5">
           <%
      
          try
          {
              
            String sql="select * from WQS_MST_LAB ORDER BY LAB_CODE";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strTestId=results.getString("LAB_CODE");
	        //String strCusDesc=results.getString("CUSTOMER_TYPE_DESC");
               
                out.println("<option value='" + strTestId + "'>"+strTestId+"</option>");   
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
            <td class="table">District Code: </td>
          <td class="table">
                    <select id="txtDistrictCode" name="txtDistrictCode" maxlength="10">
           <%
      
          try
          {
              
            String sql="select * from WQS_MST_DISTRICT ORDER BY SOURCE_LOC_DISTRICT_CODE";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strDistrictCode=results.getString("SOURCE_LOC_DISTRICT_CODE");
	        //String strCusDesc=results.getString("CUSTOMER_TYPE_DESC");
               
                out.println("<option value='" + strDistrictCode + "'>"+strDistrictCode+"</option>");   
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
            <td class="table">Sample Type: </td>
          <td class="table">
                    <select id="txtSampleType" name="txtSampleType" maxlength="5">
           <%
      
          try
          {
              
            String sql="select * from WQS_MST_SAMPLE_TYPES ORDER BY SAMPLE_TYPE_ID";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strSampId=results.getString("SAMPLE_TYPE_ID");
	        //String strCusDesc=results.getString("CUSTOMER_TYPE_DESC");
               
                out.println("<option value='" + strSampId + "'>"+strSampId+"</option>");   
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
            <td class="table">Scheme Type: </td>
          <td class="table">
                    <select id="txtSchemeType" name="txtSchemeType" maxlength="10">
           <%
      
          try
          {
              
            String sql="select * from WQS_MST_SCHEME ORDER BY SCHEME_TYPE";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strSchemeType=results.getString("SCHEME_TYPE");
	        //String strCusDesc=results.getString("CUSTOMER_TYPE_DESC");
               
                out.println("<option value='" + strSchemeType + "'>"+strSchemeType+"</option>");   
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
            <td class="table">Source Type: </td>
          <td class="table">
                    <select id="txtSourceType" name="txtSourceType" maxlength="10">
           <%
      
          try
          {
              
            String sql="select * from WQS_MST_SOURCE ORDER BY SOURCE_TYPE";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strSourceType=results.getString("SOURCE_TYPE");
	        //String strCusDesc=results.getString("CUSTOMER_TYPE_DESC");
               
                out.println("<option value='" + strSourceType + "'>"+strSourceType+"</option>");   
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
            <td class="table">Programme Type: </td>
          <td class="table">
                    <select id="txtProgType" name="txtProgType" maxlength="10">
           <%
      
          try
          {
              
            String sql="select * from WQS_MST_PROGRAMME_MAIN_TYPES ORDER BY PROGRAMME_MAIN_TYPE_ID";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strProgType=results.getString("PROGRAMME_MAIN_TYPE_ID");
	        //String strCusDesc=results.getString("CUSTOMER_TYPE_DESC");
               
                out.println("<option value='" + strProgType + "'>"+strProgType+"</option>");   
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
            <td class="table">Sub Programme Type: </td>
          <td class="table">
                    <select id="txtSubProgType" name="txtSubProgType" maxlength="10">
           <%
      
          try
          {
              
            String sql="select distinct PROGRAMME_SUBTYPE_ID from WQS_MST_PROGRAMME_SUBTYPES ORDER BY PROGRAMME_SUBTYPE_ID";
             results=statement.executeQuery(sql);
            
              while(results.next())
              {
                String strSubProgType=results.getString("PROGRAMME_SUBTYPE_ID");
	        //String strCusDesc=results.getString("CUSTOMER_TYPE_DESC");
               
                out.println("<option value='" + strSubProgType + "'>"+strSubProgType+"</option>");   
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
                <td colspan="4" class="tdH" align="center">
                  <input type="submit" value="Submit"/>
                  <input type="reset" value="Clear"/>
                  <input type="button" value="Exit" onclick="closeWindow()"/>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form></body>
</html>