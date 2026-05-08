<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%-- <%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>--%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Query Based on Service Record</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
   
    
    <script type="text/javascript" src="../scripts/SR_Based_QueryJS.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
    </style>
  </head>
  <%
  
  Connection connection=null;
  PreparedStatement ps=null;
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
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
  <body><form name="frmSR_Based_Query" action="SR_Based_QueryServ.rep">
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <th colspan="2" align="center">Query Based on Service Record</th>
        </tr>
        <tr class="tdH">
          <th colspan="2" align="left">Select Office</th>
        </tr>
       
        <tr class="table">
          <td>
            <div align="left">
              Office Id 
              <font color="#e70000">*</font>
            </div>
          </td>
          <td>
            <div align="left">
              <!--onkeypress="return numbersonly1(event,this);"-->
                 <input type="hidden" name="txtDept_Id" id="txtDept_Id" value="TWAD" >
              <input type="text" name="txtOffice_Id" id="txtOffice_Id"
                     onchange="return callServer1('Load','null')"
                    
                     onkeypress="return numbersonly1(event,this);"
                     maxlength="4"></input>&nbsp;<img src="../../../../../../images/c-lovi.gif"
                                                      width="20" height="20"
                                                      alt="Office_List"
                                                      onclick="jobpopup();"></img>
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <font color="#808080">Name of the Office</font>
          </td>
          <td>
            <input type="text" name="txtOffice_Name" id="txtOffice_Name"
                   style="background-color: #FFFFFF" disabled="disabled"
                   size="30"></input>
          </td>
        </tr>
        <tr class="table">
          <td>
            <font color="#808080">Office Address</font>
            <label style="color:rgb(255,0,0);"/>
          </td>
          <td>
            <textarea cols="40" rows="4" name="txtOffice_Address1"
                      id="txtOffice_Address1" style='background-color: #FFFFFF'
                      disabled="disabled"></textarea>
          </td>
        </tr>
        <tr class="tdH">
          <th colspan="2" align="left">Select Period</th>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Date From<font color="#e70000">&nbsp;</font>(dd/mm/yyyy) 
              <font color="#e70000">*</font>
            </div>
          </td>
          <td>
            <div align="left">
              <input type="text" name="txtDateFrom" id="txtDateFrom"
                     maxlength="10" size="10"
                     onfocus="javascript:vDateType='3';"
                     onblur="return checkdt(this);"
                     onkeypress="return calins(event,this);"/>
               
              <img id="fromimg" src="../../../../../../images/calendr3.gif"
                   onclick="showCalendarControl(document.frmSR_Based_Query.txtDateFrom);"
                   alt="Show Calendar"></img>
            </div>
          </td>
        </tr>
        <tr class="table">
          <td>
            <div align="left">
              Date To(dd/mm/yyyy)<font color="#ff0000">&nbsp;*</font>
            </div>
          </td>
          <td>
            <div align="left">
              <input type="text" name="txtDateTo" id="txtDateTo" maxlength="10"
                     size="10"
                     onfocus="javascript:vDateType='3';"
                     onblur="return checkdt(this);"
                     onkeypress="return calins(event,this);"/>
               
              <img id="toimg" src="../../../../../../images/calendr3.gif"
                   onclick="showCalendarControl(document.frmSR_Based_Query.txtDateTo);"
                   alt="Show Calendar"></img>
            </div>
          </td>
        </tr>
        <tr class="tdH">
          <th colspan="2" align="left">Select Designation / Rank</th>
        </tr>
        <tr class="table">
                <td>Select</td>
                <td> <input type="radio" value="Dest"  onclick="selectoption1()"
                             name="optselect" id="optselect1" checked="checked"></input>
                       Designation
                      <input type="radio" value="Rank" name="optselect"  onclick="selectoption1()"
                             id="optselect2" ></input>
                       Rank
                </td>
        </tr>
         <tr class="table" id="divdest">
                  <td>
                    <div align="left">
                      Designation<font color="#e70000">*</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                    <select name="cmbsgroup" id="cmbsgroup" onclick="getDesignation()">
                <option value="0">Select Service Group</option>
                        <%
           ResultSet rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                
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
                    <!---  designation -->
                      <select name="cmbDesignation" id="cmbDesignation"
                              onclick="return checkGroup();">
                        <option value="0"></option>
                        
                      </select>
                    </div>
                  </td>
                </tr>
                
                
                
        
          <tr class="table" id="divrank" style="display:none">
                  <td>
                    <div align="left">
                      Rank<font color="#e70000">*</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                    <select name="cmbsgroup1" id="cmbsgroup1" onclick="getDesignation1()">
                <option value="0">Select Service Group</option>
                        <%
            rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                
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
                    <!---  rank -->
                      <select name="cmbRank" id="cmbRank"
                              onclick="return checkGroup1();">
                        <option value="0"></option>
                        
                      </select>
                    </div>
                  </td>
                </tr>
                
                 <tr class="table">
                       <td align="left">Report&nbsp;Type</td>
                       <td><select name="cmbReportType">
                              <option value="PDF" selected="selected">PDF</option>
                              <option value="EXCEL">EXCEL</option>
                            <!--  <option value="TXT">TXT</option>-->
                              <option value="HTML">HTML</option>
                            </select> 
                        </td>
                </tr>        
                
                
              <tr class="tdH">
      <td  colspan="2">
          <div align="center">
      <input type="button" id="cmdsubmit" name="Submit" value="Submit" onclick="return btnsubmit()">
        <input type=reset value=Clear>
            <input type=button value=Exit onclick="self.close()">
                
      </div>
      </td>
      </tr>
      </table>
    </form></body>
</html>