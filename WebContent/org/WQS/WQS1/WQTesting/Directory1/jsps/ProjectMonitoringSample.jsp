<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>ProjectMonitoringSample</title>
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
    <script language="javascript" src="../scripts/ProjectMonitoringSample_Js.js" type="text/javascript">
    </script>
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
                   /* ps=con.prepareStatement("select b.district_code,b.districtname from(select office_id,district_code from com_mst_offices)a inner join(select distcode_nic,district_code,districtname from com_mst_districts)b on a.district_code=b.distcode_nic where a.office_id=?");
                    ps.setInt(1,oid);
                    rs=ps.executeQuery();
                    if(rs.next())
                    {
                        did=rs.getString("district_code");
                        System.out.println("district code:"+did);
                        dspec=rs.getString("districtname");
                        dist=did+"--"+dspec;
                        System.out.println("District:"+dist);
                    }*/
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
  %>
  <table border="1" width="100%" align="center">
  <tr class="tdH">
  <td colspan="2" align="center">
  Project Monitoring Entry
  </td>
  </tr>
  <tr class="table">
  <td>
  Lab <font color="Red">*</font>
  </td>
  <td>
  <input type=text name="labcode" id="labcode" value="<%=lb%>" size="40" disabled="true">
  </td>
  </tr>
  
  <tr class="table">
  <td>
  Customer Id <font color="Red">*</font>
  </td>
  <td>
  <input type="text" name="cid" id="cid" size=70 onchange="changeCustomer()">
  </td>
  </tr>
  
  <tr class="table">
  <td>
  Customer Reference No <font color="Red">*</font>
  </td>
  <td>
  <input type="text" name="refno" id="refno" size=70>
  </td>
  </tr>
  
  <tr class="table">
  <td>
 Date of Entry <font color="Red">*</font>
  </td>
  <td>
  <input type="text" name="date" id="date" >
  <img src="../../../../../../images/calendr3.gif" onclick="showCalendarControl(document.monitoring.date);" alt="Show Calendar" id="pur_date_cal"></img>                           
  </td>
  </tr>
  
  
  <tr class="table">
  <td>
  Resurvey District<font color="Red">*</font>
  </td>
  <td>
  <select name="distcode" id="distcode" onchange="load_block()">
  </select>
  </td>
  </tr>
  
  <tr class="table">
  <td>
  Resurvey Block <font color="Red">*</font>
  </td>
  <td>
  <select name="blockcode" id="blockcode" onchange="load_panchayat()" >
  <option value="0" >--Select Block--</option>
  <%
            /*    String query ="select blockcode,blockname from com_mst_blocks where district_code='"+did+"'";
                System.out.println(query);
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) 
                {
                     bcode=rs.getString("blockcode");
                     bdesc=rs.getString("blockname");
                     out.println("<option value='"+bcode+"'>"+bcode+"--"+bdesc+"</option>");
                }*/
  %>
  </select>
  </td>
  </tr>
  
  <tr class="table">
  <td>
  Resurvey Panchayat <font color="Red">*</font>
  </td>
  <td>
  <select name="pancode" id="pancode" onchange="load_habitation()" >
  <option value="0" >--Select Panchayat --</option>
  </select>
  </td>
  </tr>
  
  <tr class="table">
  <td>
  Resurvey Habitation <font color="Red">*</font>
  </td>
  
    <td>
  <select name="habitationcode" id="habitationcode" >
  <option value="0" >--Select Habitation --</option>
  </select>
  </td>
  
  </tr>
  
  <tr class="table">
  <td>
  Scheme Type <font color="Red">*</font>
  </td>
    <td>
  <select name="schemetype" id="schemetype" >
  <option value="0" >--Select Scheme Type --</option>
  </select>
  </td>
  </tr>
  
  
  <tr class="table">
  <td>
  Source Type <font color="Red">*</font>
  </td>
    <td>
  <select name="sourcetype" id="sourcetype" onchange="changeType()" >
  <option value="0" >--Select Source Type --</option>
  </select>
  </td>
  </tr>
  
  
  
  <tr class="table">
  <td>
  Source Code <font color="Red">*</font>
  </td>
    <td>
  <select name="sourcecode" id="sourcecode" >
  <option value="0" >--Select Source --</option>
  </select>
  </td>
  </tr>
  
  <tr class="table">
  <td>
  Programme<font color="Red">*</font>
  </td>
  
    <td>
  <select name="programmecode" id="programmecode" >
  <option value="0" >--Select Programme --</option>
  </select>
  </td>
  </tr>
  
  
  
  <tr class="table">
  <td>
  Location <font color="Red">*</font>
  </td>
  <td>
  <input type="text" name="location" id="location">
  </td>
  <tr class="table">
          <td colspan="2" align="center" height="36">
            <input type="button" name="add" value="Add" id="add" onclick="insert()" >
            <input type="button" name="update" value="Update" id="update" onclick="update_record()" >
            <input type="button" name="delete" value="Delete" id="delete" onclick="delete_record()">
            <input type="button" value="  Clear  " onclick="clr()"/>
          </td>
          
        </tr>
        <tr class="table">
          <td colspan="2">&nbsp;</td>
          
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="exit" value="  Exit  " id="exit" onclick="javascript:self.close();"/>
          </td>
   </tr>
  </tr>
  </table>
  <table border="1">
  <tr class="tdTitle"><td colspan="14">Existing Details</td></tr>
  <tr class="tdH">
  <td>
        Select
    </td>
    <td>
        LabCode
    </td>
     <td>
        Customer Id
    </td>
    <td>
        Customer Ref No
    </td>
     <td>
        Date of Entry
    </td>
    <td>
        Programme Code
    </td>
    
     <td>
        District Code
    </td>
    
     <td>
        Block Code
    </td>
    
     <td>
        Panchayat  Code
    </td>
    
    <td>
        Habitation  Code
    </td>
    
    <td>
        Scheme Type
    </td>
    
    <td>
        Source Type
    </td>
    
    <td>
        Source Code
    </td>
    
    <td>
        Location
    </td>
    
    
  </tr>
  <tbody id="tbody" class='table'>
  </tbody>
  </table>
    </form>
  </body>
</html >
