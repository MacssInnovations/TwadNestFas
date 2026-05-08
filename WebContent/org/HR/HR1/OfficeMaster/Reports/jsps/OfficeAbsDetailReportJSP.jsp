<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page  session ="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Directory of All Offices</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
    </style>
        <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript" src="../scripts/OfficeAbsDetailReportJS.js"></script>
    <script type="text/javascript" language="javascript">
    window.toolbar=true;
    </script>
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
  <body><form name="frmOfficeDetailRep"
              action="../../../../../../OfficeAbsDetailReportServ.rep"
              method="POST">
      <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="80%">
          <tr class="tdH">
            <th align="center" colspan="2">
              Directory of All Offices
            </th>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Selection Filter</div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                <input type="radio" name="optoffdetail" checked="checked"
                       value="ALLABS" onclick="activatespecific()"/>
                 No. of Offices - Level wise
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                <p>
                  <input type="radio" name="optoffdetail" value="REGIONABS"
                         onclick="activatespecific()"/>
                   No. of Offices - Region&nbsp;wise&nbsp; 
                  <input type="radio" name="optoffdetail" value="REGIONDETAIL"
                         onclick="activatespecific()"/>
                   Details of Region
                </p>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                <input type="radio" name="optoffdetail" value="SEL"
                       onclick="activatespecific()"/>
                 Report upto 
                <select name="cmblevel" disabled="disabled">
                  <option value="CL" selected="selected">CIRCLE</option>
                  <option value="DN">DIVISION</option>
                  <option value="SD">SUBDIVISION</option>
                  <option value="SN">SECTION</option>
                </select>
                 
                <p>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <input type="radio" name="optsel" value="ABS"
                         checked="checked" disabled="disabled"/>Abstract
                                                                Report&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <input type="radio" name="optsel" value="DETAILS"
                         disabled="disabled"/>
                   Detailed&nbsp;Report
                </p>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td colspan="2">
              <div align="left">
                <input type="radio" name="optoffdetail" value="SPECIFIC"
                       onclick="activatespecific()"></input>Specific Offices
              </div>
            </td>
          </tr>
          <tr class="table" id="divoffice" style="display:none">
            <td colspan="2">
              <div align="left">
                <table>
                  <tr class="table">
                    <td colspan="5">
                      <table width="100%">
                        <tr class="table">
                          <td>
                            <div align="left">
                              Select Office Level&nbsp;&nbsp;&nbsp; 
                              <select name="cmbolevel" id="cmbolevel"
                                      onchange="getRegion()">
                                <option value="">Select Office Level</option>
                                <%
           ResultSet rs=null;
           try
           {
           ps=connection.prepareStatement("select OFFICE_LEVEL_ID,OFFICE_LEVEL_NAME from COM_MST_OFFICE_LEVELS where OFFICE_LEVEL_ID !='SN'and OFFICE_LEVEL_ID !='LB'    order by OFFICE_LEVEL_NAME");
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
               // connection.close();
          
          }    
                
        %>
                              </select>
                            </div>
                          </td>
                        </tr>
                      </table>
                    </td>
                  </tr>
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
                      <div id="divsubdiv" style="visibility:hidden">Sub Division</div>
                    </td>
                    <td>
                      <div id="divsection" style="visibility:hidden">Section</div>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <select name="cmbregion" id="cmbregion"
                              style="visibility:hidden" onchange="getCircle()"></select>
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
                              style="visibility:hidden" onchange="getSection()"></select>
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
              <div align="left">Report Type 
                <select name="cmbReportType">
                  <option value="PDF" selected="selected">PDF</option>
                  <option value="EXCEL">EXCEL</option>
                <!--  <option value="TXT">TXT</option>-->
                  <option value="HTML">HTML</option>
                </select>
               </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="button" name="Submit" value="SUBMIT" id="cmdsubmit"
                       onclick="btnsubmit()"/>
                 
                <input type="button" name="cmdreset" value="CANCEL"
                       id="cmdreset" onclick="btncancel()"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>