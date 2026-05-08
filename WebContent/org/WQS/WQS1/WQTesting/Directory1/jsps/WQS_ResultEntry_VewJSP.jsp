<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_ResultEntry_VewJSP</title>
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
    <script language="javascript" src="../scripts/WQS_ResultEntry_VewJS.js" type="text/javascript">
    </script>
  </head>
  <body onload="changeSample()">
  <form name="ResultEntry">
  <%
            int invoice_no=Integer.parseInt(request.getParameter("invoice"));
            int sample_no=Integer.parseInt(request.getParameter("sample"));
            String purpose_id=request.getParameter("purpose_id");
            out.println("<input type=hidden name=inv id=inv value="+invoice_no+">");
            out.println("<input type=hidden name=smp id=smp value="+sample_no+">");
            out.println("<input type=hidden name=test_purpose id=test_purpose value='"+purpose_id+"'>");
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
  <table cellspacing="2" cellpadding="3" border="1" width="110%" align="center" class="table">
  <tr class="tdH">
    <td colspan="2" align="center">
        Water Sample Result Entry
    </td>
  </tr>
  <tr class="table">
      <td width="30%">
        Lab
      </td>
      <td width="80%">
        <input type=text name="labcode" id="labcode" value="<%=lb%>" size="40" disabled="disabled">
      </td>
  </tr>
  
  <tr class="table">
      <td width="30%">
        Invoice Number
      </td>
      <td width="80%">
        <input type="text" name="ino" id="ino" size=20 disabled="disabled">
      </td>
  </tr>
  
  <tr class="table">
      <td width="30%">
        Sample Number
      </td>
      <td width="80%">
        <input type="text" name="sample" id="sample" disabled="disabled">
      </td>
  </tr>  
  <tr class="table">
      <td width="30%">
          Date of Collection
      </td>
      <td width="80%">
          <input type="text" name="cdate" id="cdate" style="background-color:rgb(214,214,214)" readonly="readonly">
      </td>
  </tr>  
  <tr class="table">
      <td width="30%">
            District
      </td>
      <td width="80%">
            <input type="text" name="distcode" id="distcode" size="30" style='background-color:rgb(214,214,214)' readonly="readonly">
      </td>
  </tr>
  <tr class="table">
      <td width="30%">
            Location Type
      </td>
      <td width="80%">
            <input type="text" name="ltype" id="ltype" size="20" style='background-color:rgb(214,214,214)' readonly="readonly">
      </td>
  </tr>
  <tr class="table">
      <td width="30%">
                Local Body
      </td>
      <td width="80%">
                <input type="text" name="lbody" id="lbody" size="50" style='background-color:rgb(214,214,214)' readonly="readonly">
      </td>
  </tr>
  <tr class="table">
      <td width="30%">
           Block
      </td>
      <td width="80%">
          <input type="text" name="blockcode" id="blockcode" size="40" style='background-color:rgb(214,214,214)' readonly="readonly">
      </td>
  </tr>
  
  <tr class="table">
      <td width="30%">
           Panchayat
      </td>
      <td width="80%">
          <input type="text" name="pancode" id="pancode" size="40" style='background-color:rgb(214,214,214)' readonly="readonly">
      </td>
  </tr>
  
  <tr class="table">
      <td width="30%">
            Habitation
      </td>
      <td width="80%">
          <input type="text" name="habitationcode" id="habitationcode" size="50" style='background-color:rgb(214,214,214)' readonly="readonly">
      </td>
  </tr>
  <tr class="table">
      <td width="30%">
            Scheme Type
      </td>
      <td width="80%">
            <input type="text" name="schemetype" id="schemetype" size="50" style='background-color:rgb(214,214,214)' readonly="readonly">         
      </td>
  </tr>
  <tr class="table">
      <td width="30%">
          Source Type
      </td>
      <td width="80%">
          <input type="text" name="sourcetype" id="sourcetype" size="45" style='background-color:rgb(214,214,214)' readonly="readonly">       
      </td>
  </tr>
  <tr class="table">
      <td width="30%">
          Source Code
      </td>
      <td width="80%">
          <input type="text" name="sourcecode" id="sourcecode" size="30" style='background-color:rgb(214,214,214)' readonly="readonly">     
      </td>
  </tr>
  
  <tr class="table">
      <td width="30%">
          Programme
      </td>
      <td width="80%">
          <input type="text" name="programmecode" id="programmecode" size="50" style='background-color:rgb(214,214,214)' readonly="readonly">         
      </td>
  </tr>
  <tr class="table">
      <td width="30%">
          Sampling Point
      </td>
      <td width="80%">
          <input type="text" name="spoint" id="spoint" size="50" style='background-color:rgb(214,214,214)' readonly="readonly">         
      </td>
  </tr>
  <tr class="table">
    <td width="30%">
         Sample Location
    </td>
    <td width="80%">
         <input type="text" name="location" id="location" size="70" maxlength="100" style='background-color:rgb(214,214,214)' readonly="readonly">
    </td>
  </tr>
  <tr class="table">
      <td width="30%">
          Test Purpose
      </td>
      <td width="80%">
          <input type="text" name="purpose" id="purpose" size="60" style='background-color:rgb(214,214,214)' readonly="readonly">          
      </td>
  </tr>
  <tr class="table">
      <td width="30%">
        Date of Receipt
      </td>
      <td width="80%">
        <input type="text" name="rdate" id="rdate" disabled="disabled" size=15/>
         Time  <input type="text" name="rtime" id="rtime" size="5" maxlength="5" disabled="disabled">
          <input type="radio" id="rt" name="rt" value="AM" checked="checked"> AM <input type="radio" id="rt" name="rt" value="PM"> PM
        
      </td>
  </tr>
  </table>
  <div id="ParameterDiv">
  <table cellspacing="2" cellpadding="3" border="1" width="110%" align="center" class="table">
    <tr>
                <td class="tdH" width="50%" colspan="2">Parameter</td>
    </tr>
    <tr>
            
                <td class="table" width="70%" colspan="2">
                        <div id="diviframeregion" style="width:70%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                </td>
    </tr>
    </table>
    </div>
    <table cellspacing="2" cellpadding="3" border="1" width="110%" align="center" class="table">
    <tr>
                <td class="tdH" width="50%" colspan="2">Potability Result</td>
    </tr>
    <tr class="table">
        <td width="40%" colspan="2" align="center">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" name="cnc" id="cnc" value="C" disabled="disabled">Contaminated &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" name="cnc" id="cnc" value="NC" disabled="disabled">Not Contaminated
            <!--C/NC
        </td>
        <td width="60%">
             <input type="text" name="cnc" id="cnc" size="30" maxlength="20">-->
        </td>
   </tr>
   <tr class="table">
        <td width="40%" colspan="2" align="center">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" name="pnp" id="pnp" value="P" disabled="disabled">Potable &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" name="pnp" id="pnp" value="NP" disabled="disabled">Not Potable
            <!--P/NP
        </td>
        <td width="60%">
             <input type="text" name="pnp" id="pnp" size="35" maxlength="20">-->
        </td>
   </tr>
   <tr>
            <td class="table" width="40%">Reason </td>
            <td class="table" width="60%">
                <textarea cols="40" rows="4" name="reason" id="reason" onkeypress="return checklength(event,this)" disabled="disabled"></textarea>
            </td>
        </tr>
  </table>
  <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center" class="tdH">
    <tr class="table">
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr class="tdH">
      <td colspan="2" align="center">
        <input type="button" name="exit" value="  Exit  " id="exit" onclick="javascript:self.close();"/>
      </td>
    </tr>
  </table>
  </form>
 </body>
</html>