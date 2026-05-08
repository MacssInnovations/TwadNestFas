<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>SELECTION OF AN OFFICE</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/JobPopupJS.js">    </script>
    <script type="text/javascript"            src="../scripts/controllingOfficeContactPopupJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"           media="screen"/>
  </head>
  <body onload="opener.forChildOption();" ondragstart = "return false" onselectstart = "return false" >
  <form name="FAS_JobSearch" id="FAS_JobSearch" method="POST">
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
      </p>
      <p>&nbsp;</p>
      <!-- Main Table -->
      <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <th align="center" colspan="2">SELECTION OF AN OFFICE</th>
        </tr>
        <tr class="tdH">
          <td>
            <input type="radio" name="optoff" id="opthier" onclick="funhierarchical()"></input>
            Select a TWAD Board Office by Hierarchical Level and Jurisdiction
            <br></br>
            <input type="radio" name="optoff" id="optlevel" onclick="funofflevel()"></input>
            Select a TWAD Board Office by Level and Work Nature
            <br></br>
            <input type="radio" name="optoff" id="optcity" onclick="funcitytown()"></input>
            Select a TWAD Board Office by City/Town Name
            <br></br>
            <input type="radio" name="optoff" id="optother" onclick="funotherdept()"></input>
            Select an Other Department Office( for deputation purposes )
            <br></br>
            <input type="radio" name="optoff" id="optcoffice" onclick="funclosedoffice()" ></input>
            Select a Closed Office
          </td>
        </tr>
        <tr>
          <td>
            <!--- 1 1 1 1 1    Hierarchical level start                       -Hierarchical level start -->
            <div align="center" id="divhier" style="display:none">
              <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td>
                    <div align="left">Select Office Level</div>
                  </td>
                  <td>
                    <div align="left"><select name="cmbolevel" id="cmbolevel"
                                           onchange="getRegion()">
                        <option value="">Select Office Level</option>
                        <%
           ResultSet rs=null;
           try
           {
           ps=connection.prepareStatement("select OFFICE_LEVEL_ID,OFFICE_LEVEL_NAME from COM_MST_OFFICE_LEVELS  where OFFICE_LEVEL_ID!='SN'  order by HIERARCHICAL_SEQUENCE");
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
                    <div align="left">Select the Office</div>
                  </td>
                  <td>
                    <div align="left">
                      <table>
                        <tr>
                          <td>
                            <div id="divregion" style="visibility:hidden">Region</div>
                          </td>
                          <td>
                            <div id="divcircle" style="visibility:hidden">Circle</div>
                          </td>
                          <td>
                            <div id="divdivision" style="visibility:hidden">Division</div>
                          </td>
                          <td>
                            <div id="divsubdiv" style="visibility:hidden">Sub
                                                                          Division</div>
                          </td>
                          <td>
                            <div id="divsection" style="visibility:hidden">Section</div>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <select name="cmbregion" id="cmbregion"
                                    style="visibility:hidden"
                                    onchange="getCircle()"></select>
                          </td>
                          <td>
                            <select name="cmbcircle" id="cmbcircle"
                                    style="visibility:hidden"
                                    onchange="getDivision()"></select>
                          </td>
                          <td>
                            <select name="cmbdivision" id="cmbdivision"
                                    style="visibility:hidden"
                                    onchange="getSubDivision()"></select>
                          </td>
                          <td>
                            <select name="cmbsubdiv" id="cmbsubdiv"
                                    style="visibility:hidden"
                                    onchange="getSection()"></select>
                          </td>
                          <td>
                            <select name="cmbsection" id="cmbsection"
                                    style="visibility:hidden"
                                    onchange="getSection1()"></select>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td colspan="2">
                    <div align="center">
                      <table align="center" cellspacing="3" cellpadding="2"
                             border="1" width="100%">
                        <tr class="tdH">
                          <td>
                            <div align="center">
                              <input type="button" id="cmdsubmit" name="Submit"
                                     value="Submit" onclick="btnsubmit()"></input>
                               
                              <input type="button" id="cmdcancel" name="cancel"
                                     value="Cancel" onclick="btncancel()"></input>
                            </div>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </td>
                </tr>
              </table>
            </div>
            <!---  1 1 1 1 1 Hierarchical level end                                     Hierarchical level end -->
            <!--- 2 2 2 2 2 2 Select by Office level and work nature start                       Select by Office level and work nature start  -->
            <div align="center" id="divofflevel" style="display:none">
              <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td rowspan="2">
                    Office Level
                    <select size="1" name="cmbControllingLevel"
                            id="cmbControllingLevel"
                            onchange="getOfficesByLevel()">
                      <option value="value">----Select OfficeLevel----</option>
                      <%
                                                      try
                                                      {
                                                        ps=connection.prepareStatement("select Office_Level_Id,Office_Level_Name from COM_MST_OFFICE_LEVELS WHERE Office_Level_Id !='SN' order by HIERARCHICAL_SEQUENCE");
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
                  <td>
                    <div id="divType1" style="visibility:hidden">Office&nbsp;Type</div>
                    <select name="cmbOfficeType" style="visibility:hidden"
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
                  <td>
                    <div id="divType2" style="visibility:hidden">Select&nbsp;Office</div>
                    <select name="cmbSelectOffice" style="visibility:hidden"
                            id="cmbSelectOffice"
                            onchange="selectControllineOffice('office')">
                      <option value="0">----Select Office----</option>
                    </select>
                  </td>
                </tr>
              </table>
            </div>
            <!---Select by Office level and work nature end                       Select by Office level and work nature end  -->
            <!---Select by City/Town start                       Select by City/Town start  -->
            <div align="center" id="divcity" style="display:none">
              <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td rowspan="2" align="left">Name of the City/Town</td>
                  <td rowspan="2" align="left">
                    <input type="text" name="txtCity" id="txtCity"
                           onchange="if(checkcity()==true)return getCityOffice();"
                           onkeypress="return nonanumber(event,this)"></input>
                  </td>
                  <td>Select the Office</td>
                </tr>
                <tr class="table">
                  <td align="left">
                    <select name="cmbCOffice" id="cmbCOffice"
                            disabled="disabled">
                      <option value="0">--- Select Office ---</option>
                    </select>
                  </td>
                </tr>
                <tr class="tdH">
                  <td colspan="3">
                    <div align="center">
                      <input type="button" id="cmdsubmit" name="Submit"
                             value="Submit" onclick="btnCitysubmit()"></input>
                       
                      <input type="button" id="cmdcancel" name="cancel"
                             value="Cancel" onclick="btncancel()"></input>
                    </div>
                  </td>
                </tr>
              </table>
            </div>
            <!--- 3 3 3 3 3 Select by City/Town end                       Select by City/Town end  -->
            <!--- 4 4 4 4 4Select by Other Dept  start                       Select by Other Dept start  -->
            <div align="center" id="divodept" style="display:none">
              <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td rowspan="2" align="left">Other Dept Name</td>
                  <td rowspan="2" align="left">
                    <select name="cmbOName" id="cmbOName"
                            onchange="getOtherOffice()">
                      <option value="0">--- Select Other Dept ---</option>
                      <%
           rs=null;
           try
           {
           ps=connection.prepareStatement("select OTHER_DEPT_ID,OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS  order by OTHER_DEPT_NAME");
            rs=ps.executeQuery();
             String strcode="";
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getString("OTHER_DEPT_ID");
                strname=rs.getString("OTHER_DEPT_NAME");
                
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
                  <td>Select the Office</td>
                </tr>
                <tr class="table">
                  <td align="left">
                    <select name="cmbOOffice" id="cmbOOffice"
                            disabled="disabled">
                      <option value="0">--- Select Office ---</option>
                    </select>
                  </td>
                </tr>
                <tr class="tdH">
                  <td colspan="3">
                    <div align="center">
                      <input type="button" id="cmdsubmit" name="Submit"
                             value="Submit" onclick="btnOthersubmit()"></input>
                       
                      <input type="button" id="cmdcancel" name="cancel"
                             value="Cancel" onclick="btncancel()"></input>
                    </div>
                  </td>
                </tr>
              </table>
            </div>
            <!---Select by Other Dept  end                       Select by Other Dept end  -->
           
           
            <!--- 4 4 4 4 4Select by Closed Office  start                       Select by Other Dept start  -->
            <div align="center" id="divCOffice" style="display:none">
              <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="table">
                  <td  align="left">Closed Office</td>
                   <td align="left">
                    <select name="cmbClosedOffice" id="cmbClosedOffice"  >
                      <option value="0">--- Select Closed Office ---</option>
                      <%
                           rs=null;
                           try
                           {
                           ps=connection.prepareStatement("select OFFICE_ID,OFFICE_NAME	 from COM_MST_OFFICES where OFFICE_STATUS_ID in ('CL','NC','RD')  order by OFFICE_NAME");
                            rs=ps.executeQuery();
                             String strcode="";
                            String strname="";          
                            while(rs.next())
                            {
                              
                               
                                strcode=rs.getString("OFFICE_ID");
                                strname=rs.getString("OFFICE_NAME");
                                
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
                  
                </tr>
                
                <tr class="tdH">
                  <td colspan="2">
                    <div align="center">
                      <input type="button" id="cmdsubmit" name="Submit"
                             value="Submit" onclick="btnClosedOfficesubmit()"></input>
                       
                      <input type="button" id="cmdcancel" name="cancel"
                             value="Cancel" onclick="btncancel()"></input>
                    </div>
                  </td>
                </tr>
              </table>
            </div>
            <!---Select by Closed Office  end                       Select by Other Dept end  -->
           
           
           
            <!-- end of the main table --><!--  cl,nc,rd-->
          </td>
        </tr>
      </table>
    </form></body>
</html>