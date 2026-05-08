<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_RegularParamJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
    <style type="text/css">
          body 
          {
                background-color: #ffffff; 
          }
          a:link { color: #002173; }
          <!--div.scroll
          {	
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
    <script language="javascript" type="text/javascript" src="../scripts/WQS_RegularParamJS.js">
    </script>
  </head>
  <body onload="loadParameter()">
         <form name="RegularRep">
   <%
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        String odt="",lb="",did="",dspec="",dist="",bcode="",bdesc=""; 
        try
          {
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
                  
              /*  System.out.println("user id::"+empProfile.getEmployeeId());
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
                }*/
  %>
   <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr>
            <td class="tdH" align="center" colspan="2"><b>Sample Result for Regular Parameters</b></td>            
        </tr>
        <tr>
            <td class="table" width="50%">Period From <font color="Red">*</font></td>
            <td class="table" width="50%">
            <input type="text" name="fdate" id="fdate">
            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.RegularRep.fdate);" alt="Show Calendar" id="pur_date_cal"></img>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Period To <font color="Red">*</font></td>
            <td class="table" width="50%">
            <input type="text" name="tdate" id="tdate">
            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.RegularRep.tdate);" alt="Show Calendar" id="pur_date_cal"></img>
            </td>
        </tr>
        <tr>
            <td class="table" width="50%">Customer Type <font color="Red">*</font></td>
            <td class="table" width="50%">
                <select name="ctype" id="ctype" onchange="displayfun()">
                    <option value="">--Select Type--</option>
                    <option value="Twad"> Twad </option>
                    <option value="Private"> Private </option>
                    <option value="Govt"> Govt </option>
                </select>
            </td>
        </tr> 
        </table>
        <div id="twaddt" style="display:none">
         <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
          <tr>
            <td class="table" align="left" width="50%">District <font color="Red">*</font></td>
            <td class="table" align="left" width="50%">
                <select name="dname" id="dname" onchange="changeDistrict()">
                    <option value="">--Select District--</option>
                     <%
                        st=con.createStatement();
                        rs=st.executeQuery("Select * from(select distinct RESURVEY_DIST_CODE from WQS_SAMPLERESULT where CUSTOMER_TYPE='Twad')a inner join(select DISTRICT_CODE,DISTRICTNAME from COM_MST_DISTRICTS)b on a.RESURVEY_DIST_CODE=b.DISTRICT_CODE");
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
            <td class="table" align="left" width="50%">Block</td>
            <td class="table" align="left" width="50%">
                <select name="block" id="block" onchange="changeBlock()">
                    <option value="">--Select Block--</option>
                </select> 
            </td>
        </tr>
         <tr>
            <td class="table" align="left" width="50%">Panchayat</td>
            <td class="table" align="left" width="50%">
                <select name="Panchayat" id="Panchayat" onchange="changePanchayat()">
                    <option value="">--Select Panchayat--</option>
                </select> 
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="50%">Habitation</td>
            <td class="table" align="left" width="50%">
                 <select name="Habitation" id="Habitation" onchange="changeHabitation()">
                    <option value="">--Select Habitation--</option>
                </select> 
            </td>
        </tr>
        </table>
        </div>
        
         <div id="otherdt" style="display:none">
         <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
         <tr>
                <td class="table" align="left" width="50%">District</td>
                <td class="table" align="left" width="50%">
                    <select name="dist" id="dist" onchange="changeDist()">
                        <option value="">--Select District--</option>
                         <%
                            st=con.createStatement();
                            rs=st.executeQuery("select distinct SOURCE_DIST_NAME from WQS_SAMPLERESULT where CUSTOMER_TYPE!='Twad'");
                            while(rs.next())
                            {
                                String dc=rs.getString("SOURCE_DIST_NAME");
                                out.println("<option value='"+dc+"'>"+dc+"</option>");
                            }     
                        %>
                    </select> 
                </td>
             </tr>
            </table>
        </div>
        <div id="loc" style="display:block">
        <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">        
        <tr>
            <td class="table" align="left" width="49%">Location</td>
            <td class="table" align="left" width="50%">
                <select name="Location" id="Location">
                    <option value="">--Select Location--</option>
                </select> 
            </td>
        </tr>
        <tr>
                
                <td class="table" align="left" width="49%">Parameter <font color="Red">*</font></td>
                <td class="table" align="left" width="49%">
                        <div id="pid">
                        </div>
                        <!--input type="checkbox" name="es" id="nitrite" value="nitrite" onclick="changeParam(this.id)">Nitrite &nbsp;&nbsp;&nbsp;Comparing Value<input type="text" name="nitriteval" id="nitriteval" disabled="disabled" size=7></input><br>
                        <input type="checkbox" name="es" id="ammonia" value="ammonia" onclick="changeParam(this.id)">Ammonia &nbsp;&nbsp;&nbsp;Comparing Value<input type="text" name="amoniaval" id="amoniaval" disabled="disabled" size=7><br>
                        <input type="checkbox" name="es" id="phosphate" value="phosphate" onclick="changeParam(this.id)">Phosphate &nbsp;&nbsp;&nbsp;Comparing Value<input type="text" name="phasphateval" id="phasphateval" disabled="disabled" size=7><br>
                        <input type="checkbox" name="es" id="tidys" value="tidys" onclick="changeParam(this.id)">Tidys &nbsp;&nbsp;&nbsp;Comparing Value<input type="text" name="tidysval" id="tidysval" disabled="disabled" size=7-->
                </td>
          </tr>
         <tr class="table">
          <td width="50%">
              Select Report Type <font color="Red">*</font>
          <td width="49%"><select name="cmbReportType" id="cmbReportType">
          
          <option value="PDF">PDF</option>
          <option value="HTML">HTML</option>
          <option value="EXCEL">EXCEL</option>
          </select>
          </td>
        </tr>
        </table>
        </div>
        <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center">   
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