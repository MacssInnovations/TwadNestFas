<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_ElementSymbolJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"          media="screen"/>
    <script language="javascript" src="../scripts/WQS_ElementSymbolJS.js" type="text/javascript">
    </script>
  </head>
  <body>
  <form name="ElementSymbol">
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
   %>
   <table border="1" width="100%"
             align="center" class="table">
        
        <tr class="tdH">
          <td colspan="2" align="center">
            <font face="Times New Roman">
              <strong>Element Symbol Directory</strong>
            </font></td>
          
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Test Purpose <font color="Red">*</font></strong>
            </font></td>
          <td width="54%">
            <select name="test_purpose" id="test_purpose" onchange="loading()">
                <option value="">--Select Test Purpose--</option>
                <%
                      try
                      {
                         System.out.println("Test Purpose");
                         st=con.createStatement();
                         rs=st.executeQuery("select test_purpose_id,test_purpose from wqs_test_purpose");
                         while(rs.next())
                         {
                          out.print("<option value='"+rs.getString("test_purpose_id")+"--"+rs.getString("test_purpose")+"'>"+rs.getString("test_purpose")+"</option>");
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
        <tr class="table">
          <td width="46%">
            <div id="snoid" style="display:none">
                <font face="Times New Roman">
                  <strong>Sno <font color="Red">*</font></strong>
                </font>
            </div>
          </td>
          <td width="54%">
            <div id="snoid1" style="display:none"><input type="text" name="sno" size="10" id="sno" disabled="disabled"/></div>
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Parameter <font color="Red">*</font></strong>
            </font></td>
          <td width="54%">
          
            <input type="text" name="es" maxlength="50" size="50" id="es" onkeyup="checkTestPurpose()" onchange="checkParam()"/>
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Description <font color="Red">*</font></strong>
            </font></td>
          <td width="54%">
            <input type="text" name="desc" maxlength="95" size="95" id="desc"/>
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Standard/Non Standard (S/A) <font color="Red">*</font></strong>
            </font></td>
          <td width="54%">
            <input type="text" name="sn" maxlength="1" size=2 id="sn" onkeyup="capitalise()"/>
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Test Type <font color="Red">*</font></strong>
            </font></td>
          <td width="54%">
            <input type="radio" name="b1" id="phy">Physical
            <input type="radio" name="b1" id="chem">Chemical
            <input type="radio" name="b1" id="bact">Bacteriological
            <input type="radio" name="b1" id="bio">Biological
          </td>
        </tr>
        <tr class="table">
          <td colspan="2" align="center" height="36">
            <input type="button"  value="  Add  " onclick="added()" id="add" name="add"/>
            <input type="button" value="  Update" onclick="upd()" id="update" name="update"/>
            <input type="button" value="  Delete  " onclick="del()" id="delet" name="delet"/>
            <input type="button" value="  Clear  " onclick="clr()"/>
          </td>
          
        </tr>
        <tr class="table">
          <td colspan="2">&nbsp;</td>
          
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="exit" value="Exit" id="exit" onclick="javascript:self.close();"/>
          </td>
        </tr>
      </table>
    <table align="center" border="1" width="100%">
      <tr class="tdTitle"><td colspan="7">Existing Details</td></tr>
      <tr class="tdH">
      <th width="10%">Edit</th><th width="10%">Sno</th><th width="20%">Parameter</th><th width="20%">Description</th><th width="20%">Standard/Non Standard</th><th width="20%">Test Type</th>
      </tr>
      <tbody id="tb" class="table">
      </tbody>
      </table>
  </form>
  </body>
</html>