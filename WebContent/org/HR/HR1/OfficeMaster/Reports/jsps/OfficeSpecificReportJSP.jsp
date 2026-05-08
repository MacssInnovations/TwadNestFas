<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page  session ="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript" src="../scripts/OfficeSpecificReportJs.js">    </script>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <link href="../../../../../../css/Sample3.css" rel="stylesheet"           media="screen"/>
 
    <title>Office Specific Report</title>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a{ color: #0000ff;
        text-decoration:none;
      }
     
    </style>
  </head>
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
  <body>
  
  <form name="OfficeSpecificReport"
              action="../../../../../OfficeSpecificReportServ.rep"
              method="POST">
      <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
            <td colspan="2" align="center"><b>Office Specific Report</b></td>
        </tr>
         <tr class="table">
                  <td align="left">Office Level</td>
                  <td  align="left">
                    <select size="1" name="cmbControllingLevel"
                            id="cmbControllingLevel"
                            onchange="getOfficesByLevel()">
                      <option value="">----Select OfficeLevel----</option>
                      <%
                                                      try
                                                      {
                                                        ps=connection.prepareStatement("select Office_Level_Id,Office_Level_Name from COM_MST_OFFICE_LEVELS order by Office_Level_Name");
                                                        results=ps.executeQuery(); 
                                                        while(results.next()) 
                                                        {
                                                            out.print("<option value='" + results.getString("Office_Level_Id") + "'>" + results.getString("Office_Level_Name") + "</option>");                      
                                                        }
                                                        results.close();
                                                      }
                                                      catch(Exception e)
                                                      {                        
                                                      }      
                                                %>
                    </select>
                  </td>
                 </tr>
                 <tr class="table" id="divown" style="display:none">
                  <td align="left">Office Work Nature</td>
                  <td align="left">
                    <select name="cmbOfficeType" 
                            onchange="getOfficesByType()">
                      <option value="0">----Select Office Type----</option>
                      <%
                                                          try
                                                          {
                                                           ps=connection.prepareStatement("select Work_Nature_Id,Work_Nature_Desc from COM_MST_WORK_NATURE order by Work_Nature_Desc");
                                                            results=ps.executeQuery(); 
                                                            while(results.next()) 
                                                            {
                                                                out.print("<option value='" + results.getString("Work_Nature_Id") + "'>" + results.getString("Work_Nature_Desc") + "</option>");                      
                                                            }
                                                            results.close();
                                                          }
                                                          catch(Exception e)
                                                          {}      
                                                    %>
                    </select>
                  </td>
                </tr>
                <tr class="table">
           <td align="left">Report&nbsp;Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbReportType">
                  <option value="PDF" selected="selected">PDF</option>
                  <option value="EXCEL">EXCEL</option>
                <!--  <option value="TXT">TXT</option>-->
                  <option value="HTML">HTML</option>
                </select> </td>
           <td align="left">
                Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbpagination" onchange="changepagesize()">
                  <option value="5" selected="selected">5</option>
                  <option value="10">10</option>
                  <option value="15">15</option>
                  <option value="20">20</option>
                </select>
           </td>
          </tr>
             
        </table>
         <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th>
              Select All <input type="checkbox" name="chkall" onclick="sellectall()"><br/>
              <a href="javascript:inverseselect()">Inverse&nbsp;Select</a>
            </th>
            <th>
             Office ID
            </th>
            <th>
             Office Name
            </th>
            <th>
             Office Level
            </th>
            <th>
             Office Address
            </th>
            
          </tr>
          <tbody id="tb" class="table">
          
         
          </tbody>
        </table>
     
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
 
   <tr class="tdH">
      <td>
       <table align="center"  cellspacing="3" cellpadding="2" border="0" width="100%" >
       <tr >
       <td width="30%">
          <div align="left"> <div id="divpre" style="display:none"></div> </div>
      </td>
       <td width="40%">
          <div align="center"><table border="0"><tr><td> <div id="divcmbpage" style="display:none" >Page&nbsp;&nbsp;<select name="cmbpage" id="cmbpage" onchange="changepage()"></select></div></td><td><div id="divpage"></div></td></tr></table> </div>
      </td>
      <td width="30%">
          <div align="right"> <div id="divnext" style="display:none"></div> </div>
      </td>
      </tr>
      </table>
      </tr>
      <tr class="tdH">
      <td >
          <div align="center">
      <input type="button" id="cmdsubmit" name="Submit" value="Submit" onclick="return btnsubmit()">
         <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();">
         
      </div>
      </td>
      </tr>
      
      </table>
      </div>
      <input type="hidden" name="txtseloff">
    </form>
    
  </body>
</html>