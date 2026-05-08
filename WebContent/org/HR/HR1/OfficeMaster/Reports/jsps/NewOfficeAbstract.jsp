<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page  session ="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Directory of Offices - Summary</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
    </style>
        <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript" src="../scripts/OfficeSummaryReportJS.js"></script>
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
  <body><form name="frmOfficeDetailRep"
              action="../../../../../../NewOfficeAbstract.rep"
              method="POST">
       <table width="100%">
       <tr>
       <td>
      <div align="center">
        <table cellspacing="2" cellpadding="3" border="1" width="100%">
          <tr class="tdH">
            <th align="center" colspan="2">
              Directory of Offices - Summary
            </th>
          </tr>
          <tr class="table">
            <td colspan="2">
              <div align="left">Selection Filter</div>
            </td>
          </tr>
          <tr class="table">
            <td colspan="2">
              <div align="left">
                <input type="radio" name="optoffdetail" checked="checked"
                       value="ALLABS" onclick="activatespecific()"/>
                 Summary of Offices - Level wise
              </div>
            </td>
          </tr>
          <tr class="table">
            <td colspan="2">
              <div align="left">
                <p>
                  <input type="radio" name="optoffdetail" value="REGIONABS"
                         onclick="activatespecific()"/>
                   Summary of Offices - Region&nbsp;wise&nbsp;Report 
              <!--    <input type="radio" name="optoffdetail" value="REGIONDETAIL"
                         onclick="activatespecific()"/>
                   Details of Region-->
                </p>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td colspan="2">
              <div align="left">
                <input type="radio" name="optoffdetail" value="SEL"
                       onclick="activatespecific()"/>
                Summary of Report upto 
                <select name="cmblevel" disabled="disabled">
                  <option value="CL" selected="selected">CIRCLE</option>
                  <option value="DN">DIVISION</option>
                  <option value="SD">SUBDIVISION</option>
                  <!--<option value="SN">SECTION</option>-->
                </select> Office
                 
                <p>
                  <input type="hidden" name="optsel" value="ABS">
                <!--  <input type="radio" name="optsel" value="ABS"
                         checked="checked" disabled="disabled"/>Abstract
                                                                Report&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                  <input type="radio" name="optsel" value="DETAILS"
                         disabled="disabled"/>
                   Detailed&nbsp;Report-->
                </p>
              </div>
            </td>
          </tr>
          <tr class="table" id="divown" style="display">
            <td>
             <div align="left">
                <input type="radio" name="optoffdetail" value="SEL2"
           onclick="activatespecific()"/>Summary of Report Office Work Nature wise
                  </div></td>
                  <td align="left">
                    <select name="cmbOfficeType" id="cmbOfficeType"
                            >
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
      </td>
      </tr>
      </table>
    </form></body>
</html>