<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
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
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> Retirement Projection Report - Month Wise</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
       <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
            
    <script type="text/javascript"
            src="../scripts/Retire_Cadre_MonthwiseJS.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
      <!--div.scroll {	
      height: 100px;	
      width: 100%;	
      overflow: auto;	
      border: 1px solid #666;	
      background-color: #fff;	
      padding: 0px;
     visibility: hidden;
     position: relative;
      }-->
      
    </style>
  </head>
  
  <body><form name="frmValidationSummaryRep" method="POST" 
                       action="../../../../../../Retire_Cadre_Monthwise_serv">
  
         
            
            <input type="hidden" name="offlevel">
            <input type="hidden" name="office">
            <input type="hidden" name="officetype">
            <input type="hidden" name="officeselected">
            <input type="hidden" name="designationlevel">
            <input type="hidden" name="designation">
            <input type="hidden" name="outputtype">
            <input type="hidden" name="ordertype">
            
  
      <div align="center">
        <table border='2' width="100%">
            <tr>
              <td colspan='2' class="tdH" align="center"><strong> Retirement Projection Report - Month Wise</strong></td>
            </tr>
          
          <tr class="table">
          <td width="25%">
            <div align="left">
              Select the Year & Month 
              <font color="#e70000">*</font>
            </div>
          </td>  
          <td align="left">
          <select name="cmbmon" id="cmbmon">
            <option value="00">--Select the Month--</option>
            <option value="01">January</option>
            <option value="02">February</option>
            <option value="03">March</option>
            <option value="04">April</option>
            <option value="05">May</option>
            <option value="06">June</option>
            <option value="07">July</option>
            <option value="08">August</option>
            <option value="09">September</option>
            <option value="10">October</option>
            <option value="11">November</option>
            <option value="12">December</option>
          </select>
          <input type="text" name="tyear" id="tyear" maxlength="4" size="6" onkeypress="return  numbersonly1(event,this)"/>
          </td>
        </tr>  
        
          <tr>
                    
             <td colspan="2" align="left" valign="top" class="table">
              <div align="center">
                <table border="0" width="100%">
                  <tr>
                    <td colspan="2" class="tdH">Report Output Format</td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      
                        <input type="radio" name="optoutputtype" value="pdf" checked></input>PDF
                                                                         Format
                      
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      
                        <input type="radio" name="optoutputtype" value="excel"></input>EXCEL
                                                                         Format
                      
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      
                        <input type="radio" name="optoutputtype" value="html"></input>HTML
                                                                         Format
                      
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr>
            <td colspan="2" class="tdH" align="center">
            <input type="submit" value="Submit" onclick="return check();">
            <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();">
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>