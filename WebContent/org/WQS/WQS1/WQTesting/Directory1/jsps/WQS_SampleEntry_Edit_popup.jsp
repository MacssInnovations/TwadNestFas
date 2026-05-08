<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>WQS_SampleEntry_Edit_popup</title>
     <link href="../../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
    <script language="javascript" src="../scripts/WQS_SampleEntry_Edit_popup.js" type="text/javascript">
    </script>
  </head>
   <body onload="loadfun()">
  <form name="invoice">
   <%
            String cmd=request.getParameter("command");
            System.out.println(cmd);
            out.println("<input type=hidden name=command id=command value='"+cmd+"'>");
            
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
   <table cellspacing="4" cellpadding="2" border="1" width="100%"
             align="center" >
         <tr class="tdH">
         <td colspan="5">
           <input type="hidden" name="labcode" id="labcode" value="<%=lb%>" size="40" disabled="true">
        </td>
        </tr>
      </table>
    <table id="Exsisting" border="1" width="100%"
             align="center">
        <tr class="tdH">
                <th width="5%">
                    Select
                </th>
                <th width="5%">
                    Invoice Number
                </th>
                 <th width="20%">
                    Invoice_date
                </th>
                <th width="20%">
                    Customer Type
                </th>
               
        </tr>
        <tbody id="tb" class="table">
        </tbody>
        </table>  
        <table border="1" width="100%"
             align="center">
        <tr class="tdH" >
        <td align="center" colspan="7">
        <input type="button" name="submit" id="submit" value="Submit" onclick="onSubmit()"></input>
        <input type="button" name="cancel" id="cancel" value="Cancel" onclick="javascript:window.close()">
        </td>
        </tr>
      </table>
      </form>
      </body>
</html>