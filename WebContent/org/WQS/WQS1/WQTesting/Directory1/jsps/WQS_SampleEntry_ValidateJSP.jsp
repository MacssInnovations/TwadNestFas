<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_SampleEntry_ValidateJSP</title>
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
    <script language="javascript" src="../scripts/WQS_SampleEntry_ValidateJS.js" type="text/javascript">
    </script>
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
              position: relative;
          }
    </style>
  </head>
  <body onload="load_labcode()">
  <form name="monitoring">
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
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
  %>
  <table id="Existing"  border="1" width="100%" align="center">
  <tr class="table">
  <td>
          <table cellspacing="2" cellpadding="3" border="0" width="100%" align="center" class="table">
          <tr class="tdH">
            <td colspan="2" align="center">
                Sample Location Entry
            </td>
          </tr>
          <tr class="table">
              <td width="40%">
                Lab <font color="Red">*</font>
              </td>
              <td width="60%">
                <input type=text name="labcode" id="labcode" value="<%=lb%>" size="40" disabled="true">       
              </td>
          </tr>  
          <tr class="table">
              <td width="40%">
                Invoice Number<font color="Red">*</font>
              </td>
              <td width="60%">
                <input type="text" name="ino" id="ino" size=15 onchange="changeInvoice()">
                <input type="hidden" name="ctype" id="ctype">
                <img src="../../../../../../images/c-lovi.gif" width="20" height="20" alt="InvoiceList" onclick="servicepopup();">&nbsp;&nbsp;&nbsp;&nbsp;
                Total Samples<input type="text" name="total" id="total" size="5" readonly="readonly" style="background-color:rgb(214,214,214)"/>
              </td>
          </tr>
          <tr class="tdH">
            <td colspan="2" align="left">
                Sample Number Details
            </td>
          </tr>
          <tr class="table">
              <td width="40%">
                Sample Type <font color="Red">*</font>
              </td>
              <td width="60%">
                <select name="stype" id="stype">
                        <option value="">--Select Sample Type--</option>
                         <%
                          try
                          {
                                 st=con.createStatement();
                                 rs=st.executeQuery("select sample_type from wqs_sample_type ");
                                 while(rs.next())
                                 {
                                    out.print("<option value='"+rs.getString("sample_type")+"'>"+rs.getString("sample_type")+"</option>");
                                 }
                           }
                           catch(Exception e)
                           {
                              out.println(e.getMessage());
                           }
                        %>
                </select>
              </td>
          </tr> 
          <tr>
            <td class="table" align="left" width="36%">Test Purpose <font color="Red">*</font></td>
            <td class="table" align="left">
            <select name="purpose" id="purpose" style="width:50%" onclick="loadTestPurpose()"><option value="">---Select Test Purpose---</option></select>
            <div class="scroll" id="divpurpose" style="width:50%;display:none">
            <%
                st=con.createStatement();
                boolean bool=false;
                rs=st.executeQuery("select test_purpose_id,test_purpose from wqs_test_purpose");
                out.println("<table cellpadding=0 cellspacing=0 border=1 width='100%'>");
                int i=0;
                while(rs.next())
                {
                    System.out.println("result:"+rs.getString("test_purpose"));
                    if(bool=!bool)
                    {
                        out.println("<tr bgcolor='pink'><td>");
                        out.println("<input type='checkbox' name='test_purpose' id='test_purpose' style='background-color:pink' value='"+rs.getString("test_purpose_id")+"--"+rs.getString("test_purpose")+"'>"+rs.getString("test_purpose"));
                        out.println("</td><td>");
                    }
                    else
                    {
                        out.println("<tr><td>");
                        out.println("<input type='checkbox' name='test_purpose' id='test_purpose' value='"+rs.getString("test_purpose_id")+"--"+rs.getString("test_purpose")+"'>"+rs.getString("test_purpose"));
                        out.println("</td><td>");
                    }
                    i++;
                }
                out.println("</table>");
            %>
            </div>
            </td>
          </tr>
          <tr class="table">
              <td width="40%">
                Sample Number<font color="Red">*</font>
              </td>
              <td width="60%">
                <input type="text" name="sample" id="sample" />
              </td>
          </tr>
          <tr class="table">
              <td width="40%">
                  Date of Collection <font color="Red">*</font>
              </td>
              <td width="60%">
                  <input type="text" name="cdate" id="cdate">
                  <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.monitoring.cdate);" alt="Show Calendar" id="pur_date_cal" ></img> 
                  Time  <input type="text" name="ctime" id="ctime" size="5" maxlength="5">
                  <input type="radio" id="ct" name="ct" value="AM" checked="checked"> AM <input type="radio" id="ct" name="ct" value="PM"> PM
              </td>
          </tr>
          
          
          <tr class="table">
              <td width="40%">
                    District<font color="Red">*</font>
              </td>
              <td width="60%">
                    <select name="distcode" id="distcode">
                    <option value="0">--Select District--</option>
                    </select>
              </td>
          </tr>
          <tr class="table">
              <td width="40%">
                    Location Type <font color="Red">*</font>
              </td>
              <td width="60%">
                    <select name="ltype" id="ltype" >
                    <option value="0">--Select Location Type--</option>
                    <option value="Corporation">Corporation</option>
                    <option value="Municipality">Municipality</option>
                    <option value="UTP">UTP</option>
                    <option value="RTP">RTP</option>
                    <option value="VP">VP</option>
                    </select>
              </td>
          </tr>
          </table>
          <div id="local" style="display:none">
          <table cellspacing="2" cellpadding="3" border="0" width="100%" align="center">
          <tr class="tdH">
            <td colspan="2" align="left">
                     Local Boby Details 
            </td>
          </tr>
          <tr class="table">
              <td width="40%">
                        Local Body
              </td>
              <td width="60%">
                        <select name="lbody" id="lbody">
                        <option value="0">--select--</option>
                        </select>
              </td>
          </tr>
          </table>
          </div>
          <div id="village" style="display:none">
          <table cellspacing="2" cellpadding="3" border="0" width="100%" align="center">
          <tr class="tdH">
            <td colspan="2" align="left">
                Village Panchayat Details
            </td>
          </tr>
          <tr class="table">
              <td width="40%">
                   Block 
              </td>
              <td width="60%">
                  <select name="blockcode" id="blockcode">
                  <option value="0">--Select Block--</option>
                  </select>
              </td>
          </tr>
          
          <tr class="table">
              <td width="40%">
                   Panchayat 
              </td>
              <td width="60%">
                  <select name="pancode" id="pancode">
                  <option value="0" >--Select Panchayat --</option>
                  </select>
              </td>
          </tr>
          
          <tr class="table">
              <td width="40%">
                    Habitation 
              </td>
              <td width="60%">
                  <select name="habitationcode" id="habitationcode" >
                  <option value="0" >--Select Habitation --</option>
                  </select>
              </td>
          </tr>
          </table>
          </div>
          <div id="source_div" style="display:none">
          <table cellspacing="2" cellpadding="3" border="0" width="100%" align="center">
          <tr class="table">
              <td width="40%">
                    Scheme Type 
              </td>
              <td width="60%">
                    <select name="schemetype" id="schemetype">
                    <option value="0" >--Select Scheme Type --</option>
                    </select>
              </td>
          </tr>  
          <tr class="table">
              <td width="40%">
                  Programme
              </td>
              <td width="60%">
                  <select name="programmecode" id="programmecode">
                  <option value="0" >--Select Programme --</option>
                  </select>
              </td>
          </tr>  
           <tr class="table">
              <td width="40%">
                  Sampling Point
              </td>
              <td width="60%">
                  <select name="spoint" id="spoint">
                  <option value="0" >--Select Sampling Point --</option>
                  </select>
              </td>
          </tr>
          </table>
          </div>
          <table cellspacing="2" cellpadding="3" border="0" width="100%" align="center">         
          <tr class="table">
              <td width="40%">
                  Source Type 
              </td>
              <td width="60%">
                  <select name="sourcetype" id="sourcetype">
                  <option value="0" >--Select Source Type --</option>
                  </select>
              </td>
          </tr>
          <tr class="table">
              <td width="40%">
                  <div id="source_code_div"> Source Code </div>
              </td>
              <td width="60%">
                  <div id="source_code_div1">
                        <select name="sourcecode" id="sourcecode" >
                            <option value="0" >--Select Source --</option>
                        </select>
                  </div>
              </td>
          </tr>
          <tr class="table">
            <td width="40%">
                 Sample Location 
            </td>
            <td width="60%">
                 <input type="text" name="location" id="location" size="70" maxlength="100">
            </td>
          </tr>
          <tr class="table">
                      <td colspan="2" align="center" height="36">
                        <input type="button" name="validate" value="Validate" id="validate" onclick="validate_record()">
                        <input type="button" id="clr" value="  Clear  " onclick="clarfun()"/>
                        <input type="button" name="exit" value="  Exit  " id="exit" onclick="javascript:self.close();"/>
                      </td>
          </tr>
          </table>
        </td>
    </tr>
  </table>
  <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
  <tr class="tdH">
  <td width="5%">
        Select
  </td>
    <td width="5%">
        Sample Number
    </td>
    <td width="5%">
        Sample Type
    </td>    
    <td width="5%">
        Test Purpose
    </td>
     <td width="5%">
        Date of Collection
    </td>
    <td width="10%">
        District
    </td>
     <td width="5%">
        Location Type
    </td>
     <td width="10%">
        Local Body
    </td>
     <td width="10%">
        Block
    </td>
    <td width="10%">
        Panchayat
    </td>
    <td width="10%">
        Habitation
    </td>
    <td width="5%">
        Scheme Type
    </td>    
    <td width="5%">
        Programme
    </td>
    <td width="5%">
        Sampling Point
    </td>
    <td width="5%">
        Sampling Location
    </td>
  </tr>
  <tbody id="tbody" class='table'>
  </tbody>
  </table>
    </form>
  </body>
</html>