<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_SampleResult_RepJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_SampleResult_RepJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"          media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
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
  <body>
  <form name="SampleResult">
  <%
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        String odt="",lb="",did="",dspec="",dist="",bcode="",bdesc=""; 
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
    
              //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
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
                int  oid=0,odidt=0;
                        
                try
                {
           
                    ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                    ps.setInt(1,empid);
                    rs=ps.executeQuery();
                    if(rs.next()) 
                    {
                            oid=rs.getInt("OFFICE_ID");
                        
                    }
                    rs.close();
                    ps.close();
                    ps=con.prepareStatement("select LAB_CODE,LAB_DESC from WQS_MST_LAB where LAB_CODE=?");
                    ps.setInt(1,oid);
                    rs=ps.executeQuery();
                    if(rs.next()) 
                    {
                        odidt=Integer.parseInt(rs.getString("LAB_CODE"));
                        odt=rs.getString("LAB_DESC");
                        lb=odidt+"--"+odt;
                        System.out.println(lb);
                    }
                    rs.close();
                    ps.close();
                    ps=con.prepareStatement("select b.district_code,b.districtname from(select office_id,district_code from com_mst_offices)a inner join(select distcode_nic,district_code,districtname from com_mst_districts)b on a.district_code=b.distcode_nic where a.office_id=?");
                    ps.setInt(1,oid);
                    rs=ps.executeQuery();
                    if(rs.next())
                    {
                        did=rs.getString("district_code");
                        dspec=rs.getString("districtname");
                        dist=did+"--"+dspec;
                        System.out.println("District:"+dist);
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
  %>
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr>
            <td class="tdH" align="center" colspan="2"><b>Sample Result</b></td>            
        </tr>
        <!--tr>
            <td class="table" align="left" width="49%">Lab</td>
            <td class="table" align="left" width="51%">
                 <input type=text name="lab" id="lab" value="<%=lb%>" size="40" disabled="true" onchange="changeLab()"></td>
        </tr-->
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
            <td class="table" align="left" width="49%">Location</td>
            <td class="table" align="left" width="51%">
                <select name="loc" id="loc">
                    <option value="">--Select--</option>
                    <%
                        st=con.createStatement();
                        rs=st.executeQuery("Select distinct LOCATION from WQS_SAMPLERESULT");
                        while(rs.next())
                        {
                            String loc=rs.getString("LOCATION");
                            out.println("<option value='"+loc+"'>"+loc+"</option>");
                        }     
                    %>
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
           <tr>
                <td class="table" align="left" width="49%">Value Option</td>
                <td class="table" align="left" width="49%">
                    <input type="radio" name="opt" id="opt1" value="Equal">=</input>
                    <input type="radio" name="opt" id="opt2" value="Lessthan"><</input>
                    <input type="radio" name="opt" id="opt3" value="Greaterthan">></input>
                    <input type="radio" name="opt" id="opt4" value="Lessthanequal"><=</input>
                    <input type="radio" name="opt" id="opt5" value="Greaterthanequal">>=</input>
                </td>
          </tr>
          <tr>
            <td class="table" align="left" width="49%">Comparing Value</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="cval" id="cval">
            </td>
         </tr>
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