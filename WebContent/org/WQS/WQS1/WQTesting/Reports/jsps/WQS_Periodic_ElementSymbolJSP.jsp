<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_Periodic_ElementSymbolJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <style type="text/css">
          body 
          {
                background-color: #ffffff; 
          }
          a:link { color: #002173; }
          
          div.scroll
          {	
              height: 100px;	
              width: 100%;	
              overflow: auto;	
              border: 1px solid #666;	
              background-color: #fff;	
              padding: 0px;
             visibility: hidden;
             position: relative;
          }
      
    </style>
    <script language="javascript" type="text/javascript" src="../scripts/WQS_Periodic_ElementSymbolJS.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
  </head>
  <body onload="loadData()">
  <form name="SampleResult">
   <%
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        String odt="",lb="",dspec="",dist="",bcode="",bdesc=""; 
        int did=0;
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
                    ps=con.prepareStatement("select distinct district_code," + 
                           "(case when district_code is null then (select office_name from com_mst_offices where office_id=a.office_id) else '-' end" + 
                           ")as off_name," + 
                           "(case when district_code is not null then (select district_name from com_mst_districts where district_code=b.district_code) else '-' end" + 
                           ")as dist_name from" + 
                    "(select office_id,designation_id from hrm_emp_current_posting where office_id=?" + 
                    ")a left outer join" + 
                    "(select office_id,district_code from com_mst_offices" + 
                    ")b on a.office_id=b.office_id");
                    ps.setInt(1,oid);
                    rs=ps.executeQuery();
                    if(rs.next()) 
                    {
                       did=rs.getInt("district_code");
                       if(rs.getString("dist_name").equalsIgnoreCase("-")) 
                       {
                            String distname[]=rs.getString("off_name").split(",");
                            System.out.println(distname.length);
                            System.out.println("dname from off_name:"+distname[distname.length-1].trim());
                            dspec=distname[distname.length-1].trim();
                       }
                       else
                       {
                            dspec=rs.getString("dist_name");
                            System.out.println("dname from district name:"+dspec);
                       }
                    }
                    rs.close();
                    ps.close();
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
  %>
   <table class="table" cellspacing="2" cellpadding="3" border="0" width="100%" align="center">
        <tr>
            <td class="tdH" align="center" colspan="2"><b>Sample Result for All Parameters</b></td>            
        </tr>
      <tr>
            <td class="table" align="left" width="36%">Lab</td>
            <td class="table" align="left" >
                <input type="text" name="lab" id="lab" value="<%=lb%>" size="50" disabled="disabled">
            </td>
        </tr>
        <tr>
            <td class="table" width="36%">Period From <font color="Red">*</font></td>
            <td class="table">
            <input type="text" name="fdate" id="fdate">
            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.SampleResult.fdate);" alt="Show Calendar" id="pur_date_cal"></img>
            </td>
        </tr>
        <tr>
            <td class="table" width="36%">Period To <font color="Red">*</font></td>
            <td class="table">
            <input type="text" name="tdate" id="tdate">
            <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.SampleResult.tdate);" alt="Show Calendar" id="pur_date_cal"></img>
            </td>
        </tr>         
        <tr>
            <td class="table" width="36%">Customer Type <font color="Red">*</font></td>
            <td class="table">
                <select name="ctype" id="ctype" onchange="displayfun()">
                    <option value="">--Select Type--</option>
                </select>
            </td>
        </tr>
        <tr class="table">
              <td width="36%">
                   <div id="lt" style="display:none"> Location Type </div>
              </td>
              <td>
                   <div id="ltb" style="display:none">
                    <input type="radio" name="ltype" id="ltype" value="Corporation" onclick="changeLocationType(this.value)">Corporation
                    <input type="radio" name="ltype" id="ltype" value="Municipality" onclick="changeLocationType(this.value)">Municipality
                    <input type="radio" name="ltype" id="ltype" value="UTP" onclick="changeLocationType(this.value)">UTP
                    <input type="radio" name="ltype" id="ltype" value="RTP" onclick="changeLocationType(this.value)">RTP
                    <input type="radio" name="ltype" id="ltype" value="VP" onclick="changeLocationType(this.value)">VP
                   </div>
              </td>
        </tr>
        </table>
        <div id="twaddt" style="display:none">
        <table class="table" cellspacing="2" cellpadding="3" align="center" border="0" width="100%"> 
            <tr>
                <td class="tdH" align="left" colspan="2"><b>Location Details</b></td>            
            </tr>
            <tr>
                <td class="table" align="left" width="36%">District <font color="Red">*</font></td>
                <td class="table" align="left">
                    <select name="dname" id="dname" onchange="changeDistrict()">
                        <option value="">--Select District--</option>
                    </select> 
                </td>
           </tr>     
           <tr>
                <td colspan="2" align="center">
                    <div id="lb" style="display:none">
                        <table class="table" cellspacing="2" cellpadding="3" border="0" width="100%" align="center">  
                            <tr>
                                <td class="table" align="left" width="36%">Local Body</td>
                                <td class="table" align="left">
                                    <select name="lbody" id="lbody" style="display:none" onchange="changeLocalBody()">
                                        <option value="">--Select LocalBody--</option>
                                    </select> 
                                </td>
                            </tr> 
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <div id="villagepanchayat" style="display:none">
                        <table class="table" cellspacing="2" cellpadding="3" border="0" width="100%" align="center">  
                                <tr>
                                    <td class="table" align="left" width="36%">Block</td>
                                    <td class="table" align="left">
                                        <select name="block" id="block" onchange="changeBlock()">
                                            <option value="">--Select Block--</option>
                                        </select> 
                                    </td>
                                </tr>
                                 <tr>
                                    <td class="table" align="left" width="36%">Panchayat</td>
                                    <td class="table" align="left">
                                        <select name="Panchayat" id="Panchayat" onchange="changePanchayat()">
                                            <option value="">--Select Panchayat--</option>
                                        </select> 
                                    </td>
                                </tr>
                                <tr>
                                    <td class="table" align="left" width="36%">Habitation</td>
                                    <td class="table" align="left">
                                         <select name="Habitation" id="Habitation" onchange="changeHabitation()">
                                            <option value="">--Select Habitation--</option>
                                        </select> 
                                    </td>
                                </tr>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="table" align="left" colspan="2"></td>            
            </tr>
            <tr>
                <td class="table" align="left" width="36%">Location</td>
                <td class="table" align="left">
                    <select name="Location" id="Location">
                        <option value="">--Select Location--</option>
                    </select> 
                </td>
             </tr>
            <tr>
                    <td class="table" align="left" width="36%">Parameter <font color="Red">*</font></td>
                    <td class="table" align="left">
                     <select name="param" id="param" style="width:45%" onclick="getElement()">
                        <option value="0">Select Parameter</option>
                    </select>
                        <div class="scroll" id="diviframeregion" style="width:45%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </div>
                    </td>
              </tr>   
              <tr>
                    <td class="table" align="left">Value Option <font color="Red">*</font></td>
                    <td class="table" align="left">
                        <input type="radio" name="opt" id="opt" value="Equal">=</input>
                        <input type="radio" name="opt" id="opt" value="Lessthan"><</input>
                        <input type="radio" name="opt" id="opt" value="Greaterthan">></input>
                        <input type="radio" name="opt" id="opt" value="Lessthanequal"><=</input>
                        <input type="radio" name="opt" id="opt" value="Greaterthanequal">>=</input>
                    </td>
              </tr>
              <tr>
                    <td class="table" align="left">Comparing Value <font color="Red">*</font></td>
                    <td class="table" align="left">
                        <input type="text" name="cval" id="cval">
                    </td>
             </tr>
        </table>
        </div>        
        <table class="table" cellspacing="2" cellpadding="3" align="center" border="1" width="100%"> 
            <tr class="tdH">
              <td colspan="2" align="center">
              <input type="button" value="Generate Report" onclick="gen_rep()"/>
              <input type="button" value="Exit" onclick="javascript:self.close();"/>
              </td>
            </tr>        
    </table>
   </form>
  </body>
</html>