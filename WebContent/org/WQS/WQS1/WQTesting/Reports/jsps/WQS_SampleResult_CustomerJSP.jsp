<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_SampleResult_CustomerJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
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
    <script type="text/javascript" src="../scripts/WQS_SampleResult_CustomerJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
  </head>
  <body>
   <form name="SampleResult">
  <%
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try
          {
              //Class.forName("oracle.jdbc.OracleDriver");
              //con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","test","test");
              ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
              String ConnectionString="";
    
              String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
              String strdsn=rb.getString("Config.DSN");
              String strhostname=rb.getString("Config.HOST_NAME");
              String strportno=rb.getString("Config.PORT_NUMBER");
              String strsid=rb.getString("Config.SID");
              String strdbusername=rb.getString("Config.USER_NAME");
              String strdbpassword=rb.getString("Config.PASSWORD");
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
              //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
              Class.forName(strDriver.trim());
              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());             
              System.out.println("Connected THRO JSP");
          }
          catch(Exception e)
          {
            System.out.println(e.getMessage());
          }
    
            HttpSession session=request.getSession(false);
            UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
              
            System.out.println("user id::"+empProfile.getEmployeeId());
            int empid=empProfile.getEmployeeId();
  %>
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr>
            <td class="tdH" align="center" colspan="2"><b>Sample Result</b></td>            
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Lab</td>
            <td class="table" align="left" width="51%">
                <select name="lab" id="lab">
                    <option value="">--Select--</option>
                    <%
                        st=con.createStatement();
                        rs=st.executeQuery("Select LAB_CODE,LAB_DESC from WQS_MST_LAB");
                        while(rs.next())
                        {
                            int lcode=Integer.parseInt(rs.getString("LAB_CODE"));
                            String ldesc=rs.getString("LAB_DESC");
                            {
                                out.println("<option value='"+lcode+"--"+ldesc+"'>"+lcode+"--"+ldesc+"</option>");
                            }
                        }     
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Period From</td>
            <td class="table" width="50%">
            <input type="text" name="fdate" id="fdate">
            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.SampleResult.fdate);" alt="Show Calendar" id="pur_date_cal"></img>                           
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Period To</td>
            <td class="table" width="50%">
            <input type="text" name="tdate" id="tdate">
            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.SampleResult.tdate);" alt="Show Calendar" id="pur_date_cal"></img>                           
            </td>
        </tr> 
        <tr>
            <td class="table" align="left" width="49%">District</td>
            <td class="table" align="left" width="51%">
                <select name="dname" id="dname" onchange="changeDistrict()">
                    <option value="">--Select District--</option>
                     <%
                        st=con.createStatement();
                        rs=st.executeQuery("Select * from (select distinct RESURVEY_DIST_CODE from WQS_SAMPLERESULT where CUSTOMER_TYPE='Twad')a inner join(select DISTRICT_CODE,DISTRICTNAME from COM_MST_DISTRICTS)b on a.RESURVEY_DIST_CODE=b.DISTRICT_CODE");
                        while(rs.next())
                        {
                            String dc=rs.getString("RESURVEY_DIST_CODE");
                            String dn=rs.getString("DISTRICTNAME");
                            out.println("<option value='"+dc+"--"+dn+"'>"+dc+"--"+dn+"</option>");
                        }     
                    %>
                </select> 
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Block</td>
            <td class="table" align="left" width="51%">
                <select name="block" id="block" onchange="changeBlock()">
                    <option value="">--Select Block--</option>
                </select> 
            </td>
        </tr>
         <tr>
            <td class="table" align="left" width="49%">Panchayat</td>
            <td class="table" align="left" width="51%">
                <select name="Panchayat" id="Panchayat" onchange="changePanchayat()">
                    <option value="">--Select Panchayat--</option>
                </select> 
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Habitation</td>
            <td class="table" align="left" width="51%">
                 <select name="Habitation" id="Habitation" onchange="changeHabitation()">
                    <option value="">--Select Habitation--</option>
                </select> 
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Location</td>
            <td class="table" align="left" width="51%">
                <select name="Location" id="Location">
                    <option value="">--Select Location--</option>
                </select> 
            </td>
        </tr>
         <tr>
                <td class="table" align="left" width="49%">Parameter</td>
                <td class="table" align="left" width="49%">
                        <select name="es" id="es" style="width:45%" onclick="getElement()">
                            <option value="0">Select Element Symbols</option>
                        </select>
                        <div class="scroll" id="diviframeregion" style="width:45%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                </td>
          </tr>
              <!-- <select name="es" id="es">
                    <option value="">--Select Element Symbol--</option>
                     <%
                        st=con.createStatement();
                        rs=st.executeQuery("Select ELEMENT_SYMBOL from WQS_ELEMENT_SYMBOL");
                        while(rs.next())
                        {
                            String es=rs.getString("ELEMENT_SYMBOL");
                            out.println("<option value='"+es+"'>"+es+"</option>");
                        }     
                    %>
                </select>
            </td>
        </tr>-->
         <tr class="table">
          <td width="51%">
              Select Report Type
          <td width="49%"><select name="cmbReportType" id="cmbReportType">
          
          <option value="PDF">PDF</option>
          <option value="HTML">HTML</option>
          <option value="EXCEL">EXCEL</option>
          </select>
          </td>
        </tr>
        <tr class="table">
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
          <input type="submit" value="Generate Report" onclick="gen_rep()"/>
          <input type="button" value="Exit" onclick="javascript:self.close();"/>
          </td>
        </tr>        
        </table>
        </form>
  </body>
</html>