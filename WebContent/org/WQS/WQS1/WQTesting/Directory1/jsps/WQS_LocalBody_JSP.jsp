<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_LocalBody_JSP</title>
   <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_LocalBody_JS.js"></script>
  </head>
  <body>
  <form name="LocalBody" action="">
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
               
   %>
    <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center">
        
        <tr class="tdH">
          <td colspan="2" align="center">
            <font face="Times New Roman">
              <strong>Local Body Details</strong>
            </font></td>
          
        </tr>
        <tr class="table">
          <td width="46%" colspan="2" align="center">
                <input type="radio" id="ltype" name="ltype" onclick="callDistrict(0)">Corporation
                <input type="radio" id="ltype" name="ltype" onclick="callDistrict(1)">Municipality
                <input type="radio" id="ltype" name="ltype" onclick="callDistrict(2)">UTP
                <input type="radio" id="ltype" name="ltype" onclick="callDistrict(3)">RTP
          </td>
        </tr>
        <tr class="table">
          <td width="46%" colspan="2">
            <font face="Times New Roman">
              <strong>District</strong>
            </font>
            <select id="district" name="district" disabled="disabled" onchange="changeDistrict()">
            <option value="0">---select district---</option>
            <%
                 try
                  {
                     System.out.println("chemical");
                     st=con.createStatement();
                     rs=st.executeQuery("select district_code,district_name from com_mst_districts");
                     while(rs.next())
                     {
                      out.print("<option value='"+rs.getString("district_code")+"'>"+rs.getString("district_name")+"</option>");
                     }
                   }
                   catch(Exception e)
                   {
                      out.println(e.getMessage());
                   }
            %>
            </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <font face="Times New Roman">
                  <strong> Local Body</strong>
            </font>&nbsp;&nbsp;&nbsp;
            <select name="lbody" id="lbody" disabled="disabled" onchange="changeLocalBody()">
                <option value="0">---Select LocalBody---</option>
            </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <font face="Times New Roman">
              <strong>Designation</strong>
            </font>
            <select name="design" id="design" disabled="disabled">
                <option value="0">---Select Designation---</option>
            </select>
          </td>
        </tr>
        <tr class="table">
          <td colspan="2">&nbsp;</td>
          
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="button" value="  Submit  " onclick="selectLocalBody()"/>
            <input type="button" name="exit" value="  Exit  " id="exit" onclick="javascript:self.close()"/>
            
          </td>
         
        </tr>
      </table>
    </form>
  </body>
</html>