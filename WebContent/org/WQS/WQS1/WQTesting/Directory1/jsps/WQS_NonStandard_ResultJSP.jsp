<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_NonStandard_ResultJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" src="../scripts/WQS_NonStandard_ResultJS.js" type="text/javascript">
    </script>
  </head>
  <body onload="loading()">
    <form name="StdResult">
   <table cellspacing="3" cellpadding="2" border="1" width="90%"
             align="center">
        
        <tr class="tdH">
          <td colspan="2" align="center">
            <font face="Times New Roman">
              <strong>Non Standard Result</strong>
            </font></td>
          
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Parameter</strong>
            </font></td>
          <td width="54%">
            <select name="es" id="es">
                <option value="0">--Select Parameter--</option>
                <%
                        Connection con=null;
                        PreparedStatement ps=null;
                        ResultSet rs=null;
                        try
                        {
                                       ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                       String ConnectionString="";
                
                                       String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                                       String strdsn=rs1.getString("Config.DSN");
                                       String strhostname=rs1.getString("Config.HOST_NAME");
                                       String strportno=rs1.getString("Config.PORT_NUMBER");
                                       String strsid=rs1.getString("Config.SID");
                                       String strdbusername=rs1.getString("Config.USER_NAME");
                                       String strdbpassword=rs1.getString("Config.PASSWORD");
                                       ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                                       //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                                       Class.forName(strDriver.trim());
                                       con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                                       try
                                       {
                                            con.clearWarnings();
                                       }
                                       catch(SQLException e)
                                       {
                                            System.out.println("Exception in creating statement:"+e);
                                       }
                          }
                          catch(Exception e)
                          {
                             System.out.println("Exception in opening connection:"+e);
                          }
                          try
                          {
                          
                                 String sql="select ELEMENT_SYMBOL from WQS_ELEMENT_SYMBOL where STD_NONSTD=? order by SNO";
                                 ps=con.prepareStatement(sql);
                                 ps.setString(1,"N");
                                 rs=ps.executeQuery();
                                 while(rs.next())
                                 {
                                    String strelement=rs.getString("ELEMENT_SYMBOL");
                                    out.println("<option value='" + strelement + "'>"+strelement+"</option>");   
                                 }
                        }
                        catch(Exception e)
                        {
                          System.out.println("Exception in resultset:"+e);
                        }
                %>
            </select>
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Standard Code</strong>
            </font></td>
          <td width="54%">
            <select name="scode" id="scode" onchange="checkAvail()">
            <option value="">--select--</option>
            <option value="BIS">BIS</option>
            <option value="WHO">WHO</option>
            <option value="CPHE">CPHE</option>
            <option value="PRACTICAL">PRACTICAL</option>
            </select>
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Desirable Value</strong>
            </font></td>
          <td width="54%">
            <input type="text" name="dv" size="10" id="dv"/>
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong>Maximum Value</strong>
            </font></td>
          <td width="54%">
            <input type="text" name="mv" size="10" id="mv"/>
          </td>
        </tr>
        <tr class="table">
          <td colspan="5" align="center" height="36">
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
           <table align="center" border="1" width="90%">
      <tr class="tdTitle"><td colspan="5">Existing Details</td></tr>
      <tr class="tdH">
      <th width="20%">Edit</th><th width="20%">Parameter</th><th width="20%">Standard Code</th><th width="20%">Desirable Value</th><th width="20%">Maximum Value</th>
      </tr>
      <tbody id="tb" class="table">
      </tbody>
      </table>
  </form>
  </body>
</html>