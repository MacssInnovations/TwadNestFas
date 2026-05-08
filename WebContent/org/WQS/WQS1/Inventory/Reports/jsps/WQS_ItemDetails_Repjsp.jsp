<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_ItemDetails_Repjsp</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_ItemDetails_Repjs.js"></script>
  </head>
  <body>
  <form name="ItemDetails" action="">
  <%
        Connection con=null;
        Statement st=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
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
             ///ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
  %>
  <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
        <tr>
            <td class="tdH" align="center" colspan="2"><b>Item Details</b></td>            
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Category</td>
            <td class="table" align="left" width="51%">
                <select name="cat" id="cat" onchange="changeCat()">
                    <option value="">--Select--</option>
                    <%
                        st=con.createStatement();
                        rs=st.executeQuery("select * from (select distinct MAJOR_CATEGORY_CODE from WQS_MST_INV_SUP_ITEM)a inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE");
                        while(rs.next())
                        {
                            String catdesc=rs.getString("MAJOR_CATEGORY_DESC");
                            out.print("<option value='"+catdesc+"'>"+catdesc+"</option>");
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="table" align="left" width="49%">Item</td>
            <td class="table" align="left" width="51%">
                <select name="item" id="item">
                    <option value="">--Select--</option>
                </select>
        </tr>
        <tr class="table">
          <td width="51%">
            <font face="Times New Roman" size="4">
              Select Report Type
            </font></td>
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
          <input type="button" value="Generate Report" onclick="gen_rep()"/>
          <input type="button" value="Exit" onclick="javascript:self.close();"/>
          </td>
        </tr>        
        </table>
        </form>
  </body>
</html>