<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_ResultEntry_ValidateJSP</title>
    <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>        
    <script type="text/javascript" src="../HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
    <script language="javascript" src="../scripts/WQS_ResultEntry_ValidateJS.js" type="text/javascript">
    </script>
  </head>
  <body>
  <form name="ResultEntry">
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
 <table cellspacing="2" cellpadding="3" border="1" width="100%" align="center" class="table">
  <tr class="tdH">
    <td colspan="2" align="center">
        Water Sample Result Entry
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
        <img src="../../../../../../images/c-lovi.gif" width="20" height="20" alt="InvoiceList" onclick="servicepopup();">
      </td>
  </tr>
   <tr class="table">
      <td width="40%">
        Total Samples
      </td>
      <td width="60%">
         <input type="text" name="total" id="total" size="5" readonly="readonly" style="background-color:rgb(214,214,214)"/>
      </td>
  </tr>
  <tr class="table">
      <td width="40%">
        Customer_type
      </td>
      <td width="60%">
        <input type="text" name="ctype" id="ctype"/>
      </td>
  </tr>
  <tr class="table">
    <td width="40%">
         Invoice Amount
    </td>
    <td width="60%">
         <input type="text" name="amt" id="amt">
    </td>
  </tr>
  <tr class="table">
          <td colspan="2" align="center" height="36">                        
          </td>
  </table>
  <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
  <tr class="tdH">
    <td width="5%">
        Sample Number
    </td>
    <td width="15%">
        Test Purpose
    </td>
     <td width="5%">
        Date of Receipt
    </td>
    <td width="10%">
        District
    </td>
    <td width="15%">
        Location
    </td>
    <td width="5%">
        View
    </td>
  </tr>
  <tbody id="tbody" class='table'>
  </tbody>
  <tr class="tdH">
          <td align="center" colspan="17">
            <input type="button" name="validate" value="Validate" id="validate" onclick="validate_record()">
            <input type="button" id="clr" value="  Clear  " onclick="clearAll()"/>
            <input type="button" name="exit" value="  Exit  " id="exit" onclick="javascript:self.close();"/>
          </td>
  </tr>
  </table>
    </form>
  </body>
</html>