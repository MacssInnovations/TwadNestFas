<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>List of all other Department offices</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>                
    <style type="text/css">
     <!-- div.scroll {	
      height: 100px;	
      width: 100%;
      overflow: auto;	
      border: 1px solid #666;	
      background-color: #fff;	
      padding: 0px;
      position: relative;
      }-->
      </style>
    <script>
        function enableCheck()
         {
         //alert('comes here ');
         document.getElementById("divEnableCheck").style.display='block';
         }
         
         function officeselectAll()
         {

            if(document.frmReport.txtDept_Id)
             {
      
            
            for(i=0;i<document.frmReport.txtDept_Id.length;i++)
            {
                    document.frmReport.txtDept_Id[i].checked=true;
                    
            }
            
               }
           }
           
           function othdeptclose()
           {
    
               var iframe=document.getElementById("divEnableCheck");
               iframe.style.visibility='hidden';
  
            }
    </script>
    
    
  </head>
  <body class="table">
  <form action="../../../../../../OtherDeptOfficeList_Serv"   name="frmReport"
                  method="post" >
                  
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
                  
                  
                  
                  
      <table border="1" cellspacing="2" cellpadding="1" width="100%">
        <tr>
          <td class="tdH">
            <center>
              <b>List of all other Department offices</b>
            </center>
          </td>
        </tr>
        
        
        <tr>
          <td>
            <table border="1" cellspacing="2" cellpadding="1" width="100%">
              <tr>
                <td >Select Other Department Office</td>
                <td>
                  <!-- <input type="hidden"  name="txtDetail"   id="txtDetail" >-->
                     
          <select name="cmbDept_Id"  id="cmbDept_Id" onclick="enableCheck()"  >
          <option value="0">Select the Department</option>
          </select>
                         <div class='scroll' id='divEnableCheck'  style='display:none'>
                        
                         <%
                         ResultSet rs=null;
                         String html="";
                         try
                         {
                         ps=connection.prepareStatement("select a.OTHER_DEPT_ID,a.OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS a  order by OTHER_DEPT_NAME");
                        rs=ps.executeQuery();
                        String strcode="";
                        String strname="";     
                        int count=0;
                        html="<table cellpadding=0 cellspacing=0 border=0 width='100%'>";
                        html=html+"<tr><td  colspan='2'><a href='javascript:officeselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:othdeptclose()'>Close</a></td></tr>";
                         boolean bool=false;
                        while(rs.next())
                        {
                            strcode=rs.getString("OTHER_DEPT_ID");
                            strname=rs.getString("OTHER_DEPT_NAME");
                            
                           // out.println("<option value='"+strcode+"'>"+strname+"</option>");
                          // out.println("<input type='checkbox' name='txtDept_Id' id='txtDept_Id' value='"+strcode+"'>"+strname+"</input>");
                            if(bool=!bool)
                            {
                                
                                html=html+"<tr bgcolor=\"pink\"><td><input type='checkbox' id='txtDept_Id' name='txtDept_Id' value='"+strcode+"'></td>";
                                html=html+"<td>"+strname+"</td></tr>";
                            }
                            else 
                            {
                                
                                html=html+"<tr ><td><input type='checkbox' name='txtDept_Id' id='txtDept_Id' value='"+strcode+"'></td>";
                                html=html+"<td>"+strname+"</td></tr>";
                            }
                            count++;
                            
                         }
                        if(count==0)
                        {
                        html="There is no Other offices ";
                        }
                        
                         html=html+"</table>"; 
                         out.println(html);
                      }
                      catch(Exception e)
                      {
                        System.out.println("Exception in grid.."+e);
                      }
                       finally
                      {
                            rs.close();
                            ps.close();
                            //connection.close();
                      
                      }    
                         
                         %>
                     <!--    </select>-->
                  </div>
                </td>           
                
              </tr>
              
              
              
              <tr>
                <td>Report Option:</td>
                <td colspan=3>
                  <input type="radio" name="txtoption" id="txtoption"
                         value="PDF" checked="checked"/>
                  PDF
                  <input type="radio" name="txtoption" id="txtoption"
                         value="EXCEL"/>
                  Excel
                  <input type="radio" name="txtoption" id="txtoption"
                         value="HTML"/>
                  HTML
                </td>
              </tr>
              <tr>
                <td colspan="4" class="tdH" align="center">
                  <input type="submit" value="Submit"/>
                  <input type="reset" value="Clear"/>
                 <!-- <input type="button" value="Exit" onclick="closeWindow()"/>-->
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form></body>
</html>